FROM eclipse-temurin:17-jdk-alpine AS builder

# Build arguments
ARG PROFILE

# Add labels for better container metadata
LABEL maintainer="Ítalo Manzine <italomanzine@gmail.com>"
LABEL version="1.0.0"
LABEL description="Manager API for mentoria backend"

# Set working directory
WORKDIR /app-api

# Copy only the files needed for dependency resolution
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw package -Dmaven.test.skip=true

# Production stage
FROM eclipse-temurin:17-jre-alpine

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Set environment variables with defaults
ENV PROFILE=${PROFILE:-prod}
ENV JAVA_OPTS=${JAVA_OPTS:-"-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+OptimizeStringConcat -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/heapdump.hprof"}

# Set working directory
WORKDIR /app-api

# Copy the built artifact from builder stage
COPY --from=builder /app-api/target/*.jar /app-api/mentoria-backend-api.jar

# Use non-root user
USER spring:spring

# Expose the application port
EXPOSE 9000

# Add healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:9000/actuator/health || exit 1

# Run the application
CMD java $JAVA_OPTS -jar /app-api/mentoria-backend-api.jar