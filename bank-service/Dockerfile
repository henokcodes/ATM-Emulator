# define base docker image
FROM openjdk:11
LABEL maintainer="javaguides.net"
ADD target/emulator-0.0.1-SNAPSHOT.jar atm-emulator-springboot.jar
ENTRYPOINT ["java", "-jar", "atm-emulator-springboot.jar"]
