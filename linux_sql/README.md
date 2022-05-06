# Linux Cluster Monitoring Agent
# Introduction
The Linux cluster monitoring agent gives users the ability to track hardware and usage information for different nodes in a Linux cluster. 
This information can be used by the user to access the workload of each node in the system by querying the data stored in the database.
This tool can be helpful to determine if nodes need to be added or removed from the cluster to ensure the cluster is running efficiently.
This tool uses bash, git, docker, databases, and crontab to track data consistently in intervals of 1 minute for every node it is installed on.

# Quick Start
Follow these steps to set up the monitoring agent on each node in the system:
- Start a psql instance using psql_docker.sh
  - To create the psql docker container, run this command using the database username and password:</br> 
    `./scripts/psql_docker.sh create username password`
  - To then Start the instance run the following command:</br> 
    `./scripts/psql_docker.sh start`
- Create tables using ddl.sql
  - Execute the ddl script on the database, using a psql instance, to create the tables:</br> 
    `psql -h localhost -U postgres -d host_agent -f sql/ddl.sql`
- Insert hardware specs data into the DB using host_info.sh
  - In the terminal type the following command, using the psql host, psql port, database name, username, and password:</br> 
    `./scripts/host_info.sh host port db_name user password`
- Insert hardware usage data into the DB using host_usage.sh
  - In the terminal execute the host_info script using the same arguments as the host_info script:</br>
    `./scripts/host_info.sh host port db_name user password`
- Crontab setup
  - In the command line type `crontab -e`, to open the crontab editor
  - In the editor paste the following code, with the previously used arguments:</br>
    `* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password`

# Implementation
## Architecture
This is a cluster diagram of the system. Information on each computer is found and added to the database using the provided scripts. The User can then find the information in the database and query the results

![Linux Cluster Monitoring Agent Diagram](./assets/architecture2.png)
## Scripts
The following are the scripts used in this project:
- psql_docker.sh
  - The docker script is used to create the docker container if one does not exist, or is used to start/stop docker from running
  - The command to run the script is as follows:</br>
    `./scripts/psql_docker.sh start|stop|create [db_username][db_password]`
  - The user must enter one of the three options, Create, Start, Stop, when running the command. If a matching argument is not provided the command will return an error
  - If the user selects "Create" as the command argument the username and password for the database must also be provided otherwise the script will return an error
- host_info.sh
  - The host_info script finds your computer's system information, then inserts it into the host_info table in the database
  - This script runs once per computer to gather the system info for setting up the database
  - The command to run the script is as follows:</br>
    `./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password`
  - All the required fields must be provided otherwise the script with throw an error requesting the missing information
  - Example of the command as follows: </br>
    `./scripts/host_info.sh localhost 5432 host_agent postgres password`
- host_usage.sh
  - The host_usage script finds your systems' usage information including disk space, memory available, kernel data and the timestamp of when all the information was gathered.
  - This script is run every minute by the crontab, it gathers information as it changes to show patterns in the data
  - The command to run the script is as follows:</br>
    `./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password`
  - All the required fields must be provided otherwise the script with throw an error requesting the missing information
  - Example of the command as follows: </br>
    `./scripts/host_usage.sh localhost 5432 host_agent postgres password`
- crontab
  - the crontab is a job scheduler, it will systematically run scripts at given intervals of time as requested by the user
  - the crontab is set up tp run the host_usage script every minute once being set up
  - the command to open the crontab editor is: `crontab -e`
  - to the crontab editor add the following command: </br>
    `* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password`
    - The 5 stars tell the crontab to run this command every minute
  - To ensure it is working query the database and select all the records in the host_usage table. there should be new records every minute.
- queries.sql
  - The queries.sql file contain common queries that might be helpful for the user to run on the database to effectively use the data it provides
  - There are 3 sample queries in the file:
    - The first query displays all entries in the host_info table by which has the most total memory. This is helpful for the user to see what each system is capable of doing
    - The second query displays the average memory of each system over a period of 5 minutes. This shows the user which systems are using the most space and which systems will require additional nodes to handle any extra workload.
    - The third query is to ensure the crontab has not failed at any point during the period of time the results are being used for data analysis as this would change some results of the queries. 
      If displays any period of 5 minutes where there was less than 3 data entries given by the crontab. Since the crontab works every minute than in each 5 minute interval there should be 5 entries, anything less is the crontab failing

## Database Modeling

- | host_info        |                   |
  |------------------|--------------------|
  | id               | serial    NOT NULL |
  | hostname         | varchar   NOT NULL |
  | cpu_number       | int       NOT NULL |
  | cpu_architecture | varchar   NOT NULL |
  | cpu_model        | varchar   NOT NULL |
  | cpu_mhz          | varchar   NOT NULL |
  | l2_cache         | int       NOT NULL |
  | total_mem        | int       NOT NULL |
  | timestamp        | timestamp NOT NULL |

- | host_usage     |                    |
  |----------------|--------------------|
  | timestamp      | timestamp NOT NULL |
  | memory_free    | int       NOT NULL |
  | host_id        | serial    NOT NULL |
  | disk_io        | int       NOT NULL |
  | disk_available | int       NOT NULL |
  | cpu_kernel     | int       NOT NULL |
  | cpu_idle       | int       NOT NULL |

# Test
The bash scripts were tested by using queries to view the database was constructed correctly. The sql queries were tested by running each individual section of the query on its own to ensure that it all functions as  it should.

# Deployment
The tool has been deployed to GitHub where it can be forked and downloaded to any computer and set u using the quick setup instructions.

# Improvements
- This project should be able to be setup with a setup script that the user can run to eliminate all the lines of code they must enter using the current quick setup guide
- This tool should be able to accept any info regarding hardware changes which would change the info in the host_info table
- More sample queries should be given using the other information in the database, such as how hard the cpus are working in each system
