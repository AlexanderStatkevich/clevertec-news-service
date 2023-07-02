FROM openjdk:17-alpine
MAINTAINER Clevertec

COPY  ./build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app.jar"]

FROM gradle:7.6.1-jdk17-alpine AS builder
MAINTAINER Clevertec

WORKDIR /config-server

COPY build.gradle .
COPY settings.gradle .
COPY gradle.properties .
COPY src ./src

WORKDIR .

RUN gradle -b /config-server/build.gradle build --no-daemon

FROM openjdk:17-alpine

COPY --from=builder /config-server/build/libs/config-server-1.0.0.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app.jar"]
