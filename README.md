# Spring URL Shortener

A simple and efficient URL shortener built with Spring Boot. This application allows users to shorten long URLs, manage shortened links, and redirect to original URLs. It's designed for easy deployment and scalability.

## Features

- Shorten long URLs to compact, shareable links
- Redirect users from short URLs to their original destinations
- Documentation with Swagger
- Cache with Redis

## Getting Started

### Prerequisites

- Java 21+
- Maven or Gradle

### Build and Run

There are two supported ways to run the app:

A) Local jar (traditional)

1. **Clone the repository**
    ```bash
    git clone https://github.com/ejanerop/spring-url-shortener.git
    cd spring-url-shortener
    ```

2. **Build the application**
    ```bash
    ./mvnw package
    ```

3. **Run the application**
    ```bash
    java -jar target/spring-url-shortener-*.jar
    ```

B) Docker Compose (recommended) — using the package published for this repository

This repository publishes a container image to GitHub Container Registry at:
ghcr.io/ejanerop/spring-url-shortener:latest

1. Create a docker-compose.yml in the project root (or use the one below).

Example docker-compose.yml:
```yaml
version: "3.8"

services:
  url-shortener:
    image: ghcr.io/ejanerop/spring-url-shortener:latest
    container_name: spring-url-shortener
    restart: unless-stopped
    ports:
      - "8080:8080"
    env_file: "deploy.env"
    volumes:
      - urlshortener-data:/data

volumes:
  urlshortener-data:
```

2. Create a `deploy.env` file with the following properties:

```properties
SPRING_PROFILES_ACTIVE=prod
# Fill these properties 
DB_URL=
DB_USER=
DB_PASSWORD=
REDIS_HOST=
REDIS_PORT=
```

2. Start the service:
```bash
docker compose up -d
```
(If using older Docker Compose, run `docker-compose up -d`.)

3. Verify:
- Swagger documentation: `http://localhost:8080/swagger-ui/index.html`

Notes:
- If you prefer a different database (MySQL/Postgres), update the environment variables or compose file to include a database service and set SPRING_DATASOURCE_* accordingly.
- If you don't want to pull the published image and instead want to build locally and run via compose, add a Dockerfile to the repo (if not present) and replace the `image:` line with a `build:` block:
  ```yaml
  url-shortener:
    build: .
    image: spring-url-shortener:local
  ```

