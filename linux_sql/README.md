# Linux Cluster Monitoring Agent
This project is under development. Since this project follows the GitFlow, the final work will be merged to the master branch after Team Code Team.

Note: You are NOT allowed to copy any content from the scrum board, including text, diagrams, code, etc. Your Github will be visible and shared with Jarvis clients, so you have to create unique content that impresses your future boss??.

# Introduction
The Linux cluster monitoring agent gives users the ability to track hardware and usage information for different nodes in a Linux cluster. 
This information can be used by the user to access the workload of each node in the system by querying the data stored in the database.
This tool can be helpful to determine if nodes need to be added or removed from the cluster to ensure the cluster is running efficiently.
This tool uses bash, git, docker, databases, and crontab to track data consistently in intervals of 1 minute for every node it is installed on.

# Quick Start
Follow these steps to set up the monitoring agent on each node in the system:
- Start a psql instance using psql_docker.sh
  - To create the psql docker container, run this command using the database username and password:
  
    `./scripts/psql_docker.sh create username password`
  - 
    To then Start the instance run the following command:
  
    `./scripts/psql_docker.sh start`
- Create tables using ddl.sql
  - Execute the ddl script on the database, using a psql instance, to create the tables:

    `psql -h localhost -U postgres -d host_agent -f sql/ddl.sql`
- Insert hardware specs data into the DB using host_info.sh
  - In the terminal type the following command, using the psql host, psql port, database name, username, and password:
    
    `./scripts/host_info.sh host port db_name user password`
- Insert hardware usage data into the DB using host_usage.sh
  - In the terminal execute the host_info script using the same arguments as the host_info script:

    `./scripts/host_info.sh host port db_name user password`
- Crontab setup
  - In the command line type `crontab -e`, to open the crontab editor
  - In the editor paste the following code, with the previously used arguments:
  - `* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password`

# Implemenation
## Architecture
This is a cluster diagram of the system. Information on each computer is found and added to the database using the provided scripts. The User can then find the information in the database and query the results

![Linux Cluster Monitoring Agent Diagram](/assets/architecture.png)
## Scripts
Shell script description and usage (use markdown code block for script usage)
- psql_docker.sh
- host_info.sh
- host_usage.sh
- crontab
- queries.sql (describe what business problem you are trying to resolve)

## Database Modeling
Describe the schema of each table using markdown table syntax (do not put any sql code)
- `host_info`
- `host_usage`

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
