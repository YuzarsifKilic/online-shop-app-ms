FROM 17-alpine-jdk:amazoncorretto-17

WORKDIR /app

COPY target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]