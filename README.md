# Todo task Spring Java REST API

This is a basic Spring Boot Java REST API to manage tasks

1. Setup environment variables, excecution this on your terminal in the directory of the project

```bash
export SPRING_DATASOURCE_URL='jdbc:postgresql://localhost:5432/todoapp'
export SPRING_DATASOURCE_USERNAME='test'
export SPRING_DATASOURCE_PASSWORD='test'
```

2. Database Up

```bash
docker compose up -d
```

3. Run project

```bash
./mvnw spring-boot:run
```



