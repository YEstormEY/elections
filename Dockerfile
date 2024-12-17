# Use an official Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the pom.xml from the backend-election directory
COPY backend-election/pom.xml .

# Install dependencies first to leverage Docker's cache
RUN mvn dependency:go-offline

# Copy the source code
COPY backend-election/src ./src

# Build the application, skipping tests to speed up the build (optional)
RUN mvn clean package -DskipTests

# Use a lightweight Java image to run the application
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the built JAR file from the Maven build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
