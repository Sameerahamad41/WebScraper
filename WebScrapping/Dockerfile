
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:go-offline


COPY src ./src


RUN mvn -B clean package -DskipTests


FROM eclipse-temurin:21-jre-jammy

WORKDIR /app


COPY --from=build /app/target/*.jar app.jar


ENV PORT=8080
ENV JAVA_OPTS="-Dserver.port=${PORT}"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
