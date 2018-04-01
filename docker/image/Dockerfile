FROM microsoft/mssql-server-linux

COPY ./entrypoint.sh /usr/src/app/entrypoint.sh
COPY ./import-data.sh /usr/src/app/import-data.sh
COPY ./import-data.sql /usr/src/app/import-data.sql

RUN chmod +x /usr/src/app/import-data.sh
RUN chmod +x /usr/src/app/entrypoint.sh
 
CMD /usr/src/app/entrypoint.sh