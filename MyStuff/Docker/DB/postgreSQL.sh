docker run -d --name postgres -e "POSTGRES_PASSWORD=1234" -e "POSTGRES_USER=docker" -e "POSTGRES_DB=docker-db" -p 5432:5432 postgres
docker exec -ti postgres bash
# psql -d docker-db -U docker