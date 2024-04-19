# shorturlapi

# Docker management

## Build images

```shell
docker compose build
```

## Start project

```shell
docker compose up
```

## Start project in background

```shell
docker compose up -d
```

## Stop project
```shell
docker compose down
```

# Cassandra management

## Create keyspace
```shell
docker exec -it cass1 bash -c "cqlsh -u cassandra -p cassandra"
CREATE KEYSPACE shorturlapp WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : 3};
exit;
```
