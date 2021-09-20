--Switch to host_agent database (assume it exists)
\c host_agent;

--Create the following tables if they don't already exist
CREATE TABLE IF NOT EXISTS PUBLIC.host_info (
    id               SERIAL NOT NULL PRIMARY KEY,
    hostname         VARCHAR NOT NULL UNIQUE,
    cpu_number       SMALLINT NOT NULL,
    cpu_architecture VARCHAR NOT NULL,
    cpu_model        VARCHAR NOT NULL,
    cpu_mhz          FLOAT NOT NULL,
    L2_cache         SMALLINT NOT NULL,
    total_mem        INT NOT NULL,
    "timestamp"        TIMESTAMP NOT NULL,
);

CREATE TABLE IF NOT EXISTS PUBLIC.host_usage (
    id             SERIAL NOT NULL PRIMARY KEY,
    "timestamp"    TIMESTAMP NOT NULL,
    host_id        SERIAL NOT NULL REFERENCES host_info(id) ON DELETE CASCADE,
    memory_free    INT NOT NULL,
    cpu_idle       SMALLINT NOT NULL,
    cpu_kernel     SMALLINT NOT NULL,
    disk_io        INT NOT NULL,
    disk_available INT NOT NULL
);