FROM maven:3.8.4-openjdk-17 AS maven
WORKDIR /app
COPY . .
RUN mvn -f /app/pom.xml clean package

FROM openjdk:17 as runtime
WORKDIR /app
ENV PORT 8080
ENV SPRING_PROFILE production
COPY --from=maven /app/target/*.jar /app/app.jar
RUN chown -R 1000:1000 /app
USER 1000:1000
ENTRYPOINT ["java","-jar","-Dserver.port=${PORT}","-Dspring.profiles.active=${SPRING_PROFILE}","app.jar"]
