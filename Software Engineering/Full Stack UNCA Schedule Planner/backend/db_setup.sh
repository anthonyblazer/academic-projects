#!/bin/bash

# code to check if DB already exists
DB_NAME=schedulerdb
DB_LIST=$(psql -U $DB_USERNAME -h $DB_HOST -p $DB_PORT -lqt)
SEARCH_RESULT=$(echo $DB_LIST | grep $DB_NAME)
LENGTH=$(echo ${#SEARCH_RESULT})


if [ "$LENGTH" = "0" ]; then
  # echo "The database does not exist. Creating database..."
  psql -U $DB_USERNAME -h $DB_HOST -p $DB_PORT -c "CREATE DATABASE \"$DB_NAME\";"
fi