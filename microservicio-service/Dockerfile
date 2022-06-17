FROM amazoncorretto:8
ENV JAR_FILE /microservicio-web/target/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]