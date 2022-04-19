# define base docker image
FROM openjdk:11
LABEL maintainer="henokcodes"
ADD target/emulator-0.0.1-SNAPSHOT.jar bank-service.jar
ENTRYPOINT ["java", "-jar", "bank-service.jar"]
