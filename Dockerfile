FROM openjdk:17

COPY target/greendogdelivery-*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]