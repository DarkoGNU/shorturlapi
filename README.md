# shorturlapi

# Important tips

Make sure that you have enough memory for your Cassandra containers - I've seen total RAM usage approaching 12 GB (with default configuration). Otherwise, your containers will be stopping unexpectedly due to OOM errors.

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
