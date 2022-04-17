FROM openjdk:11-jdk-oracle
LABEL maintainer="author@javatodev.com"
VOLUME /main-app
ADD target/user-manager-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]