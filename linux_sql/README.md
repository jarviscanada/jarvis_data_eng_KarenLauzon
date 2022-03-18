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
  - The host script finds your computer's system information, then inserts it into the host_info table in the database
  - The command to run the script is as follows:</br>
    `./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password`
- host_usage.sh
- crontab
- queries.sql (describe what business problem you are trying to resolve)

## Database Modeling
Describe the schema of each table using markdown table syntax (do not put any sql code)
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
How did you test your bash scripts and SQL queries? What was the result?

# Deployment
How did you deploy your app? (e.g. Github, crontab, docker)

# Improvements
Write at least three things you want to improve
e.g.
- handle hardware update
- blah
- blah
