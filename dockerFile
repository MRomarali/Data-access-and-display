FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn -f /pom.xml clean package

FROM openjdk:17
COPY --from=build /target/*.jar app.jar
EXPOSE 8080

CMD ["java","-jar", "/app.jar"]