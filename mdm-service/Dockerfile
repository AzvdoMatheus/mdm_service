FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp
ARG DEPENDENCY=target/dependency

COPY target/mdm-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
