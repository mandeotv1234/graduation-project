FROM openjdk:21-jdk-slim AS builder

WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=builder /app/build/libs/api-gateway-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]