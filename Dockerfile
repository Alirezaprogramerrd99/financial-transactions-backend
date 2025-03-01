# Stage 1: Build the Spring Boot application using Maven
#FROM maven:3.8.5-openjdk-21 AS build

FROM jelastic/maven:3.9.5-openjdk-21 AS build

WORKDIR /app
# Copy the pom.xml to the working directory
COPY pom.xml .

# Copy the source code into the container
COPY src ./src
RUN mvn clean package -DskipTests


# Stage 2: Run the application using a lightweight JDK image
# the line below is for the compilation:
FROM openjdk:21-jdk-slim
WORKDIR /app

#This copies the JAR file built in the build stage (/app/target/demo-0.0.1-SNAPSHOT.jar) to the current /app directory of the final image and renames it to app.jar.
#RUN ls -al /app/
COPY --from=build /app/target/financial-transactions-0.0.1-SNAPSHOT.jar app.jar
#RUN ls -al /app/target/
ENTRYPOINT ["java", "-jar", "app.jar"]