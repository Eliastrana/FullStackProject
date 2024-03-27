# Start with a base image that includes the JDK
# Use the build stage to build the application
FROM openjdk:17-slim AS build

# Set the working directory in the Docker image
WORKDIR /app

# Copy the Maven pom.xml file, the Maven Wrapper script, and the .mvn directory
COPY pom.xml mvnw ./
COPY .mvn .mvn/

# Ensure the Maven Wrapper has execute permissions
RUN chmod +x ./mvnw

# Copy your application's source code into the Docker image
COPY src src

# Now, use the Maven Wrapper to build the application's JAR file
# Skipping tests to speed up the build process (remove -DskipTests if tests are needed during build)
RUN ./mvnw package -DskipTests

# Start the second stage for the final runtime image
FROM openjdk:17-slim

# Set the working directory in the runtime container
WORKDIR /app

# Copy only the built JAR from the build stage into the runtime container
COPY --from=build /app/target/*.jar app.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]

# Expose the port on which your application will run
EXPOSE 8080
