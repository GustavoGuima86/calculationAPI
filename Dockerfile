# Start with a base image containing Java runtime
FROM --platform=$BUILDPLATFORM amazoncorretto:21-alpine-jdk as gradleimage

# Create a directory
WORKDIR /app

# Copy all the files from the current directory to the image
COPY . .

# build the project avoiding tests
RUN ./gradlew clean build -x test

FROM --platform=$BUILDPLATFORM amazoncorretto:21-alpine-jdk

COPY --from=gradleimage /app/build/libs/calculation-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app
# Expose port 8080
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "app.jar"]