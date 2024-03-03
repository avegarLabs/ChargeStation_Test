FROM openjdk:17-jdk-alpine
COPY target/api-gateway-1.0-SNAPSHOT.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]