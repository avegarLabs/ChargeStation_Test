FROM openjdk:17-jdk-alpine
COPY target/user-service-1.0-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]