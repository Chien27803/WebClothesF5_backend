# Stage 1: Build with Maven
FROM maven:3.9.9-amazoncorretto-21-debian AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build all of dependencies (skip tests for faster build)
RUN mvn package -DskipTests

# Stage 2: Create final image with only JDK and JAR
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (optional, but good for documentation)
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
