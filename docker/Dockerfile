# Stage 1: Build the application
FROM maven:3.8-amazoncorretto-17 as build
WORKDIR /app
USER root

# Copy pom.xml and download dependencies first (for better caching)
#COPY pom.xml .
#COPY mvnw .
#COPY .mvn .mvn
COPY . .
RUN yum install -y tar && yum clean all


# Copy source code and build the application
COPY ./src/ /app/src

RUN mkdir -p target/extracted &&  \
    mvn install -DskipTests -Dspotless.skip=true


# Stage 2: Run the application
FROM amazoncorretto:17
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar
RUN chmod +x app.jar && \
    ls -la /app
# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]