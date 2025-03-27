# Backend

### Table of Contents

- [Running in docker](#running-in-docker)
- [Documentation](#documentation-in-swagger)
- [Services](#services)
  - [Api Gateway Service](#api-gateway-service)
  - [Eureka Discovery Service](#eureka-discovery-service)
  - [Auth Service](#auth-service)
  - [User Service](#user-service)
  - [Transaction Service](#transaction-service)
  - [Budget Service](#budget-service)
  - [Notification Service](#notification-service)
  - [Analytics Service](#analytics-service)

## Running in docker

Execute command:

```
docker-compose up --build -d
```

## Documentation in Swagger

#### In progress...

# Services

### Naming conventions

Services' names are provided as follows: `<name of service>-service`. All words separated by dashes.

Examples: `auth-service`, `api-gateway-service`, `user-service`, etc. - <b><i>These names are used in gateway</i>.</b>

All communication with services is done through api gateway.

For detailed information on requests, see [Requests using gateway](#requests)

## Api Gateway Service

The port of gateway is 8080.


### Requests

Request pattern:


`POST http://localhost:8080/api/<service-name>/<service-enpoint>`


Example request:

```
POST http://localhost:8080/api/auth-service/auth/sign-up/
```

## Eureka Discovery Service

#### In Progress...


## Auth Service

#### In Progress...


## User Service

#### In Progress...


## Transaction Service

#### In Progress...


## Budget Service

#### In Progress...


## Notification Service

#### In Progress...


## Analytics Service

#### In Progress...




