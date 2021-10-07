# Introduction 
This project is an application that leverages the JDBC API to connect with a relational database and implement aswell as perform CRUD operations (CREATE, READ, UPDATE, DELETE). The design pattern for database connectivity involves a combination of Data Access Object (DAO) and Data Transfer Objec(DTO). The Postgres relational database is provisoned by Docker while the dependencies are managed by Maven.

# Implementation 
## ER Diagram 
![ERD](./assets/ERD.png)
## Design Pattern 
- **Data Access Object**: DAO's are one of the most common patterns when dealing with databases, as they provide an abstraction layer between the JDBC code and the business logic. When used as an interface, the input and output will be a Data Transfer Object (DTO), which are encapsulated objects that provide a single domain of data. DAO's are useful for centralized databases and it computes the joins using foreign keys.
  
- **Repository**: It focuses on a single table access per class rather than accessing the entire database as a whole. This allows the user to shard the database and retrieve a fragment of the requested data from the database. It is ideal distrubuted databases and applications that are horizontally scalable.

# Test
This application used various scripts to load the database with dummy datas and was manually tested to gauge consistency. The main method in the `JDBCExecutor` class was used to test various CRUD operations and insure ACID compliance. All the data points tested had a corresponding result within the psql instance provisioned through Docker. 