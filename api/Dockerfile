FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
<<<<<<< HEAD:api/Dockerfile

=======
>>>>>>> db09fb64645de118565fbce64ec1f611b467f589:Dockerfile
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup fleetlensgroup && adduser -S fleetlensuser -G fleetlensgroup
USER fleetlensuser
COPY --chown=fleetlensuser:fleetlensgroup --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]