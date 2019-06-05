
FROM maven:3.5.2-jdk-8-alpine AS build_the_code
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package -DskipTests

FROM openjdk:8u181-jre
COPY --from=build_the_code /tmp/target/*.jar app.jar
EXPOSE 8080
EXPOSE 8443
ADD src/main/resources/application.properties /config/application.properties
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.config.location=/config/application.properties" , "-jar","/app.jar"]
