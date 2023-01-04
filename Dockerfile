FROM openjdk:11
VOLUME /tmp
ADD ./target/myProject-0.0.1-SNAPSHOT.jar myProject.jar
ENTRYPOINT ["java","-jar","./myProject.jar"]
EXPOSE 8080