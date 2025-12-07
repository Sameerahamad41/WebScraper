# === Build stage ===
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the Spring Boot module's pom.xml
COPY WebScrapping/pom.xml ./pom.xml

# Download dependencies
RUN mvn -B dependency:go-offline

# Copy the source code
COPY WebScrapping/src ./src

# Build the project (skip tests)
RUN mvn -B clean package -DskipTests

# === Run stage ===
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/*.jar app.jar

# Railway sets PORT; pass it to Spring Boot
ENV PORT=8080
ENV JAVA_OPTS="-Dserver.port=${PORT}"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
