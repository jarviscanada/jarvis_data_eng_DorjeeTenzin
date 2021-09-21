#! /bin/bash

# usage: ./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password

# CLI args
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
export PGPASSWORD=$5

# validate arguments
if [ "$#" -ne 5 ]; then
    echo -e "Error: Illegal number of parameters \n"
    echo "./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password"
    exit 1
fi

# retreive usage info for parsing
usage_info=`free --mega`
cpuidle_calc=$(top -bn1)
disk_info=`vmstat -d`
cpu_info=`vmstat -t`
hostname=$(hostname -f)
diskav=`df -m`

# parse usage data
memory_free=$(echo "$usage_info"| grep Mem: | awk '{print $4}' | xargs)
cpu_idle=$(echo "$cpuidle_calc" | egrep "%Cpu" | awk '{print int($8+0.5)}')
cpu_kernel=$(echo "$cpu_info" | sed -n '3,${p;n;n;}' | awk '{print $14}' | xargs)
disk_io=$(echo "$disk_info" | egrep "sda" | awk '{print $10}' | xargs)
disk_available=$(echo "$diskav" | egrep "/dev/sda2" | awk '{print $4}' | xargs)
timestamp=$(date "+%Y-%m-%d %H:%M:%S")

# insert into tables
insert_stmt="INSERT INTO host_usage (timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
              VALUES('$timestamp',(SELECT id FROM host_info WHERE hostname = '$hostname'),'$memory_free', '$cpu_idle','$cpu_kernel','$disk_io','$disk_available');"

#Run insert query
psql -h $psql_host -U $psql_user -d $db_name -p $psql_port -c "$insert_stmt"

exit $?