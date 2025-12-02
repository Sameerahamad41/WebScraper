# === Build stage ===
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the project (skip tests to speed up)
RUN mvn -B clean package -DskipTests

# === Run stage ===
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy jar from build stage (adjust jar name if needed)
COPY --from=build /app/target/*.jar app.jar

# Render will set PORT env; map it to server.port
ENV PORT=8080
ENV JAVA_OPTS="-Dserver.port=${PORT}"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
