## Multi-stage Dockerfile for transfer-service infrastructure
## Builds the project and produces a slim runtime image containing the Spring Boot jar.

FROM maven:3.10.1-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY .. /workspace
RUN mvn -T 1C -f /workspace/pom.xml -pl infrastructure -am -DskipTests clean package

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
ARG JAR_PATH=/workspace/infrastructure/target/infrastructure-0.0.1-SNAPSHOT.jar
COPY --from=builder ${JAR_PATH} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
