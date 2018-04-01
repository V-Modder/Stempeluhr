sleep 20s
echo 'Starting setup sqls'
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -d master -i /usr/src/app/import-data.sql