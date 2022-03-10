#! /bin/sh

#grab arguments from user
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5
export PGPASSWORD=$psql_password

#if 5 arguments weren't provided stop script and display error
if [ $# -ne 5 ]; then
    echo 'Script requires Hostname, port, db name, user, and password'
    exit 1
  fi

#grab host info and assign to variables
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)
memory_free=$(echo "$vmstat_mb" | awk '{print $4}'| tail -n1 | xargs)
cpu_idle=$(echo "$vmstat_mb" | awk '{print $15}'| tail -n1 | xargs)
cpu_kernel=$(echo "$vmstat_mb" | awk '{print $14}'| tail -n1 | xargs)
disk_io=$(vmstat -d | awk '{print $10}'| tail -n1 | xargs)
disk_available=$(df -BM / | awk '{print $4}'| tail -n1 | sed 's/M//' | xargs)
timestamp=$(vmstat -t | awk '{print $18}{print $19}'| tail -2 | xargs)

host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

insert_stmt="INSERT INTO host_usage(timestamp,host_id,memory_free,cpu_idle,cpu_kernel,disk_io,disk_available) VALUES('$timestamp', '$host_id', '$memory_free', '$cpu_idle', '$cpu_kernel, '$disk_io', '$disk_available');"

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?