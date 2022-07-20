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

lscpu_out=$(lscpu)
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "Model name:" | awk -F ":" '{print $2}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | egrep "CPU MHz:" | awk -F ":" '{print $2}' | xargs)
l2_cache=$(echo "$lscpu_out" | egrep "L2 cache:" | awk -F ":" '{print $2}' | sed 's/K//' | xargs)
total_mem=$(grep MemTotal /proc/meminfo | awk -F ":" '{print $2}' | sed 's/kB//' | xargs)
timestamp=$(date +%F_%T | sed 's/_/ /' | xargs)

insert_stmt="INSERT INTO host_info ( hostname,cpu_number,cpu_architecture,cpu_model,cpu_mhz,L2_cache,total_mem,timestamp) VALUES('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$total_mem', '$timestamp');"

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
