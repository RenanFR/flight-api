FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /workspace
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
