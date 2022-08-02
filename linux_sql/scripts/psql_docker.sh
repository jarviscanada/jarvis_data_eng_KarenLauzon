#! /bin/sh

#grab arguments from the user
export cmd=$1
export db_username=$2
export db_password=$3

#start docker but only if docker is not already running
sudo systemctl status docker || sudo systemctl start docker

#check status of the container and add the return value to the variable
docker container inspect jrvs-psql
container_status=$?

#switch case based on user input. Used to start, stop, or create the container
case $cmd in
  create)

  # if status command returns 0 container already exists
  if [ $container_status -eq 0 ]; then
		echo 'Container already exists'
		exit 1
	fi

  #If number of arguments is not 3 exits and displays requires username and password message
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

  #Create new volume and container
	docker volume create pgdata
	docker run -d \
      --name jrvs-psql \
      -e POSTGRES_PASSWORD=db_password \
      -e POSTGRES_USER=db_username \
      -v pgdata:/var/lib/postgresql/data \
      -p 5432:5432 postgres:9.6-alpine
	exit $?
	;;

  start|stop)
  #if container status is container exists, if not exit and display error message
  if [ $container_status -ne 0 ]; then
    echo 'Container has not been created'
    exit 1
  fi

  #user command either stops or starts the container
	docker container $cmd jrvs-psql
	exit $?
	;;

  *)
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
	;;
esac