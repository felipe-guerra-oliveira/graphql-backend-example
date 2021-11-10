# grapql-backend-example
Source-code of the GraphQL backend server example


### Docker Commands

- Build
```
docker build -t graphql-example/backend:1.0.0 -f Dockerfile-GraphQL .
```

- Run
```
docker run --name graphql-example -d -p 8080:8080 graphql-example/backend:1.0.0
```

- Stop
```
docker stop graphql-example
```
