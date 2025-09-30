# ----------------------------------------------------------------
# Stage 1: Build the native executable
# Replaced the old registry.access.redhat.com image with a public quay.io image
# This image contains Mandrel (GraalVM distribution), a JDK, and build tools.
# You can also use 'quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:jdk-21'
# ----------------------------------------------------------------
FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-21 AS build

# Set the working directory inside the container
WORKDIR /project

# Copy Maven wrapper files and pom.xml for dependency caching
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download all dependencies. Only runs on initial build or if pom.xml changes.
# This step helps Docker cache the dependencies layer.
RUN ./mvnw dependency:go-offline

# Copy the rest of the application source code
COPY src /project/src

# Build the native executable
# -Pnative activates the native Maven profile.
# -DskipTests skips unit tests during the native build.
RUN ./mvnw package -Pnative -DskipTests

# ----------------------------------------------------------------
# Stage 2: Create the final minimal image
# Use a minimal base image (e.g., UBI minimal) for the smallest footprint
# ----------------------------------------------------------------
FROM registry.access.redhat.com/ubi9/ubi-minimal:latest

# Define the user to run the application as
# This is a best practice for security
USER 1001

# The application's working directory
WORKDIR /work/

# Arbitrary port exposed by the application
EXPOSE 8080

# Copy the native executable from the build stage to the minimal runtime image
# The name of the runner binary is typically based on your project artifact ID
COPY --from=build /project/target/*-runner /work/application

# The native executable is the entrypoint
CMD ["./application"]