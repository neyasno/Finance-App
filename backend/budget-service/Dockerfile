FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY gradlew gradlew.bat ./
COPY gradle/ ./gradle/
COPY build.gradle settings.gradle ./
COPY src ./src

RUN chmod +x gradlew

RUN ./gradlew bootJar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]