docker volume rm dp_35010081
docker volume create dp_35010081
docker build ./Shop -t shopserver
docker build ./Warehouse -t warehouseserver
docker container run -d -p 5002:5002 -v dp:/server shopserver
docker container run -d -p 5001:5001 -v dp:/server warehouseserver