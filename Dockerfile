FROM maven as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY .env .env
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY .env .env

EXPOSE 9010

ENTRYPOINT ["java", "-jar", "app.jar"]

