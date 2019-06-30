#For recreation
docker volume rm ha09_dp_35010081
docker container rm -f $(docker container ps -a | grep shopserver | awk '{ print $1 }')
docker container rm -f $(docker container ps -a | grep warehouseserver | awk '{ print $1 }')
docker image rm shopserver warehouseserver