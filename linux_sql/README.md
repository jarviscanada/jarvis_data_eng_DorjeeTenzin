# Linux Cluster Monitoring Agent 
The Linux Cluster Monitoring Agent is a MVP tool built for the Jarvis Linux Cluster Administration (LCA) to record hardware specification and monitor resource usage. The Jarvis cluster consists of 10 linux nodes/servers running CentOS 7 and are internally connected though a network switch which uses internal IPv4 addresses for communication. The program utilizes bash agents which are installed on all the nodes to collect the relevant data to store in a central database and persisted using a psql instance. The tool is intended for the LCA team to generate reports for future resource planning purposes.

The Cluster utilizes bash scripts to initialize a PostgresSQL Docker instance, create a schema, retrive data from the nodes and populate the database. Relevant data is isolated using regular expressions and filtered using SQL queries to generate meaningful reports. Version control is achieved throgh git for the local repository and GitHub for the remote repository, in addition it adheres to Gitflow conventions. 

# Quick Start 
Start a psql instance using psql_docker.sh
```
./scripts/psql_docker.sh create|destroy|start|stop [db_username] [db_password]
```
Create tables using ddl.sql
```
$ psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```
Insert hardware specs data into the DB using host_info.sh
```
$ ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```
Insert hardware usage data into the DB using host_usage.sh
```
$ ./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```
Crontab setup
```
$ crontab -e
        * * * * * bash /home/centos/dev/jarvis_data_eng_DorjeeTenzin/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres postgres  &> /tmp/host_usage.log
```
Sample queries for a report 
```
$ psql -h psql_host -U psql_user -d psql_db -f sql/queries.sql
   
```

# Implementation 
The psql instance in Docker is initialized by the psql_docker.sh script which creates a container using the Postgres image. After successfully connecting to the database, the ddl.sql script creates a schema which includes two tables for host_info and host_usage. The host_info table stores core hardware specification like CPU architecture and CPU model. Whereas the host_usage table stores resource usage like CPU idle, free memory and more. Both tables a one to many relationship (1:M). 

The bash agent stored in each node/server contains two scripts, host_info.sh and host_usage.sh which are used to populate their respective tables in the database. These scripts use regular expressions and other bash commands to display and filter through the ouput to retrive necessary data to be inserted into the database. 
While host_info.sh is run once per node at installation, host_usage.sh triggers a crontab job every minute which creates multiple insertions into the database for each node.  


## Architecture 
![LCA Architecture](assets/LCA_Diagram.svg?raw=true)
*An illustration of the Jarvis Linux Cluster Administration (LCA) architecture*
## Scripts
### psql_docker.sh
Starts docker and creates a PostgreSQL container, if none exists. It can be used to create, destroy start and stop the container.
```
Create container:
./scripts/psql_docker.sh create [db_username] [db_password]

Start container:
./scripts/psql_docker.sh start [db_username] [db_password]

Stop container:
./scripts/psql_docker.sh stop [db_username] [db_password]

Remove container:
./scripts/psql_docker.sh destroy [db_username] [db_password]
```
### host_info.sh
Inserts the hardware specification into the database for a node. Uses ```memeinfo``` and ```lscpu``` to get the data.  
```
bash scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```
### host_usage.sh
Inserts the host usage information into the database for a node. This information is gathered using various system calls such as ```vmstat, top, free --mega, df -m```
```
bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```
### crontab
Schedules a job for host_usage.sh every minute and also stores a log in /tmp/usage.log

### queries.sql
The SQL queries file gathers an insightful report of various business questions that may be useful for an LCA to make future decisions based on the usage and hardware data collected. 

## Database Modeling
The schema for the tables in the database and their types of values are as follows:
- `host_info`

|    | Column           | Data type | Description                                   |
|----|------------------|-----------|-----------------------------------------------|
| PK | id               | Serial    | Host id                                       |
|    | hostname         | Varchar   | The name of the host machine                  |
|    | cpu_number       | Varchar   | The number of cpus                            |
|    | cpu_architecture | Varchar   | Architecture of cpu of host                   |
|    | cpu_model        | Varchar   | The model of the cpu                          |
|    | cpu_mhz          | Float     | The speed of the cpu in mhz                   |
|    | L2_cache         | Integer   | L2_cache storage in kB                        |
|    | total_mem        | Integer   | Total memory of the host in kB                |
|    | timestamp        | Timestamp | Time that host info was entered into database |


- `host_usage`

|    | Column         | Data type | Description                                     |
|----|----------------|-----------|-------------------------------------------------|
| FK | host_id        | Serial    | Id of host                                      |
|    | timestamp      | Timestamp | Timestamp when data was entered into host_usage |
|    | memory_free    | Integer   | Free memory of host in MB                       |
|    | cpu_idle       | Integer   | Unused cpu processing in percentage             |
|    | cpu_kernel     | Integer   | Percentage of cpu kernel used                   |
|    | disk_io        | Integer   | Number of disk I/O                              |
|    | disk_available | Integer   | Available space on the disk                     |


# Test
- All the bash commnds within the scripts have been indivually tested in an isolated CentOS 7 virutal machine 
- All database entries have been double checked against the actual data found within the database. 
    i.e ``` $ lscpu``` and ```# SELECT * FROM host_usage``` 
# Improvements
- More complex queries to find even more insightful information based on the database entries 
- Store a wider range of hardware and usage information 
- Set up a weekly or monthly contab job for host_info.sh in case hardware changes are made to the system. Modify the script to delete or archive previous entries when new entries are made 
- Depending on the scale of the database and the qeries, you can set up caching.