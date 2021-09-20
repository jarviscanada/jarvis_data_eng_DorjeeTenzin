#!/bin/bash

#usage: ./scripts/psql_docker.sh create|destroy|start|stop [db_username] [db_password]

# CLI args
action=$1
db_username=$2
db_password=$3


# start docker
sudo systemctl status docker || systemctl start docker

# check container status
docker container inspect jrvs-psql
container_status=$?

# user switch case to handle create, remove start or stop container or illegle action 
case $action in
	create)
	
    # check if the container is already created
	if [ $container_status -eq 0 ] 
	then
		echo 'Container already exists'
		exit 1
	fi

	# check number of CLI argsd
	if [ $# -ne 3 ]
	then
		echo 'Create require username and password'
		exit 1
	fi
	
	# Create container
	docker volume create pgdata
	docker run --name jrvs-psql -e POSTGRES_PASSWORD=$db_password -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres

    echo 'Container jrvs-psql has been created'
	exit $?
	;;
	
    destroy)
    
    # check container status; exit 1 if the container has not been created
	if [ $container_status -ne 0 ]
	then
		echo -e "Container has not been created\n"
        echo 'Please create new container'
		exit 1
	fi

    # remove container and volum 
    docker rm -f -v jrvs-psql
    docker volume rm pgdata

    echo 'docker container jrvs-psql and the associated volume pgdata has been removed'
    exit $?
	;;

	start|stop)
	# check container status; exit 1 if the container has not been created
	if [ $container_status -ne 0 ]
	then
		echo -e "Container has not been created\n"
        echo 'Please create new container'
		exit 1
	fi

	# start or stop the container
	docker container $action jrvs-psql
    echo -e "The container jrvs-psql has ${action}ed"
	exit $?
	;;

	*)
	echo -e "Illegal command\n"
	echo 'Commands: start|stop|create|destroy'
	exit 1
	;;
esac

exit 0