@echo off

docker build -t ms_sql_server ./image
docker-compose run --rm -p 127.0.0.1:1433:1433 sql 