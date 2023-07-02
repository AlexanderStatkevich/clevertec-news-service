FROM gradle:7.6.1-jdk17-alpine AS builder
MAINTAINER Clevertec

WORKDIR /user-service

COPY build.gradle .
COPY settings.gradle .
COPY gradle.properties .
COPY src ./src

WORKDIR .

RUN gradle -b /user-service/build.gradle build --no-daemon

FROM openjdk:17-alpine

COPY --from=builder /user-service/build/libs/user-service-1.0.0.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app.jar"]
