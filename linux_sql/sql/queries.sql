--- Task 1: Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)
SELECT cpu_number, id, total_mem 
FROM host_info
GROUP BY cpu_number, id
ORDER BY total_mem DESC
LIMIT 10

--- Task 2: Average used memory in percentage over 5 mins interval for each host. (used memory = total memory - free memory). 
CREATE OR REPLACE FUNCTION round5(ts timestamp) RETURNS timestamp AS
    $$
    BEGIN 
	    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int/5*interval '5min';
    END;
    $$
LANGUAGE PLPGSQL;

SELECT
    usage.host_id, info.hostname, 
    round5(usage.timestamp) AS "ts",
    AVG(
            100 * (
                info.total_mem - usage.memory_free
            ) / info.total_mem
        ) AS "avg_used_mem_percentage"
FROM
    host_usage AS "usage"
        JOIN host_info AS "info" on usage.host_id = info.id
GROUP BY
    usage.host_id,
    info.hostname,
    ts
ORDER BY
    usage.host_id,
    ts;
LIMIT 10;

--- Task 3: Detect host failure
SELECT host_id, timestamp,
	COUNT(timestamp) OVER(PARTITION BY round5(timestamp)) AS num_data_points 
FROM host_usage
ORDER BY timestamp DESC
LIMIT 10;
