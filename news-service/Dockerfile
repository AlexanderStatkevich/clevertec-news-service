FROM gradle:7.6.1-jdk17-alpine AS builder
MAINTAINER Clevertec

WORKDIR /starter-exception-handler

COPY starter-exception-handler/build.gradle .
COPY starter-exception-handler/settings.gradle .
COPY starter-exception-handler/src ./src

WORKDIR /starter-logging

COPY starter-logging/build.gradle .
COPY starter-logging/settings.gradle .
COPY starter-logging/src ./src

WORKDIR /news-service

COPY news-service/build.gradle .
COPY news-service/settings.gradle .
COPY news-service/gradle.properties .
COPY news-service/src/ ./src

WORKDIR .

RUN gradle -b /starter-exception-handler/build.gradle publishToMavenLocal --no-daemon
RUN gradle -b /starter-logging/build.gradle publishToMavenLocal --no-daemon
RUN gradle -b /news-service/build.gradle build --no-daemon

FROM openjdk:17-alpine

COPY --from=builder /news-service/build/libs/news-service-1.0.0.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app.jar"]
