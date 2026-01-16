# 1단계: 빌드 단계
FROM gradle:7.4-jdk17-alpine AS builder
WORKDIR /app

COPY gradle gradle
COPY gradlew ./
COPY build.gradle* settings.gradle* ./

RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon

COPY . .
RUN ./gradlew bootJar -x test --no-daemon

# 2단계: 실행 단계
FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
