FROM maven:latest AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package

FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /build/target/api-*.jar /app/

ENTRYPOINT ["java", "-jar", "app.jar"]