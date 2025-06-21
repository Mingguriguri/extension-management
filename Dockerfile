# ───────────────────────────────────────────────────────
# 1. 빌드 스테이지: Gradle Wrapper 이용
# ───────────────────────────────────────────────────────
FROM gradle:7.6-jdk17 AS builder
WORKDIR /home/gradle/project

# 캐시 활용을 위해 빌드 스크립트 먼저 복사
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle ./gradle

# 의존성만 먼저 다운로드
RUN ./gradlew --no-daemon dependencies

# 소스 전체 복사 후 패키징
COPY . .
RUN ./gradlew --no-daemon bootJar -x test

# ───────────────────────────────────────────────────────
# 2. 런타임 스테이지
# ───────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# prod 프로파일 기본 활성화 (필요시 override)
ENV SPRING_PROFILES_ACTIVE=prod

# builder 스테이지에서 만들어진 JAR 파일 복사
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# 애플리케이션 포트
EXPOSE 80

# 컨테이너 기동 시 실행
ENTRYPOINT ["java","-jar","/app/app.jar"]