#!/bin/bash

# usage: ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password

# CLI args
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# validate args
if [[ "$#" -ne 5 ]] ; then
  echo -e "Error: Illegal number of parameters, requires 5"
  echo './scripts/host_info.sh psql_host psql_port db_name psql_user psql_password'
  exit 1
fi

# get host hardware information
#hostname=$`hostname -f | sed 's/^.//'
hostname=$(hostname -f)
meminfo_out=`cat /proc/meminfo`
lscpu_out=`lscpu`

# parse hardware data 
cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model name:" | cut -d ":" -f 2 | xargs)nb
cpu_mhz=$(echo "$lscpu_out"  | egrep "(CPU)\s(MHz)" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}' | sed 's/K//' | xargs)
total_mem=$(echo "$meminfo_out" | egrep "^MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(date +"%Y-%m-%d %H:%M:%S")

# SQL insert
insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, timestamp)
          VALUES ('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, $total_mem, '$timestamp')"

# migrate insertions to database
export PGPASSWORD=$psql_password
psql -h "$psql_host" -p "$psql_port"-d "$db_name" -U "$psql_user" -c "$insert_stmt"

exit $?