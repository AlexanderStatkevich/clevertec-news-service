FROM openjdk:17-alpine
MAINTAINER Clevertec


COPY  ./build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Xmx200m", "-jar", "/app.jar"]

