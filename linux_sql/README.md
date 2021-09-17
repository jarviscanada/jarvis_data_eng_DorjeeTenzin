# Linux Cluster Monitoring Agent 
The Linux Cluster Monitoring Agent is a MVP tool built for the Jarvis Linux Cluster Administration (LCA) to record hardware specification and monitor resource usage. The Jarvis cluster consists of 10 linux nodes/servers running CentOS 7 and are internally connected though a network switch which uses internal IPv4 addresses for communication. The program utilizes bash agents which are installed on all the nodes to collect the relevant data to store in a central database and persisted using a psql instance. The tool is intended for the LCA team to generate reports for future resource planning purposes.

The Cluster utilizes bash scripts to initialize a PostgresSQL Docker instance, create a schema, retrive data from the nodes and populate the database. Relevant data is isolated using regular expressions and filtered using SQL queries to generate meaningful reports. Version control is achieved throgh git for the local repository and GitHub for the remote repository, in addition it adheres to Gitflow conventions. 

# Quick Start 
Start a psql instance using psql_docker.sh
```
```
Create tables using ddl.sql
```
```
Insert hardware specs data into the DB using host_info.sh
```
```
Insert hardware usage data into the DB using host_usage.sh
```
```
Crontab setup
```
```
Sample queries for a report 
```
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
Starts docker and creates a PostgreSQL container, if none exists. It can be used to create, start and stop the container.
```
```
### host_info.sh
Inserts the hardware specification into the database for a node.
```
```
### host_usage.sh
Inserts the host usage information into the database for a node.
```
```
### crontab
Schedules a job for host_usage.sh everyone minute

### queries.sql

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
| PK | id             | Serial    | Host id                                         |
| FK | host_id        | Serial    | Id of host                                      |
|    | timestamp      | Timestamp | Timestamp when data was entered into host_usage |
|    | memory_free    | Integer   | Free memory of host in MB                       |
|    | cpu_idle       | Integer   | Unused cpu processing in percentage             |
|    | cpu_kernel     | Integer   | Percentage of cpu kernel used                   |
|    | disk_io        | Integer   | Number of disk I/O                              |
|    | disk_available | Integer   | Available space on the disk                     |


# Test

# Deployment 

# Improvements


