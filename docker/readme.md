# SQL-Server Docker image

These image are for development only, and uses a [mssql-server-linux](https://github.com/Microsoft/mssql-docker/tree/master/linux) image!
They will create a new container every startup.
Therfore a [simple sql script](./image/import-data.sql) could recreate whatever you need.
The included batch file should do all you want, just run it.