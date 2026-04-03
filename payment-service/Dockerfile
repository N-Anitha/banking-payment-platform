# ============================================================
# payment-service/Dockerfile
# Multi-stage Docker build for Java Spring Boot
# Stage 1: Build  |  Stage 2: Runtime (smaller image)
# ============================================================

# ── Stage 1: Build Stage ──────────────────────────────────
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml first (layer caching — faster builds)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests=false -B

# ── Stage 2: Runtime Stage ────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

# Create non-root user (Security best practice for banking!)
RUN addgroup -S bankapp && adduser -S bankapp -G bankapp

WORKDIR /app

# Copy only the jar from build stage
COPY --from=builder /app/target/*.jar app.jar

# Change ownership
RUN chown bankapp:bankapp app.jar

# Switch to non-root user
USER bankapp

# Expose port
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8081/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILE}", "app.jar"]
