# syntax=docker/dockerfile:1

FROM eclipse-temurin:25-jdk AS builder

RUN install -d /app
WORKDIR /app

RUN --mount=type=bind,source=.,target=/app,rw \
    --mount=type=cache,target=/root/.gradle <<BUILD
    set -e
    mkdir -p /build
    ./gradlew clean installShadowDist --stacktrace --no-daemon
    cp -t /build/ build/libs/app-0.0.1-SNAPSHOT-all.jar
BUILD

FROM eclipse-temurin:25-jre AS runtime

RUN install -d /app
WORKDIR /app
COPY --from=builder /build/app-0.0.1-SNAPSHOT-all.jar /app/app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
