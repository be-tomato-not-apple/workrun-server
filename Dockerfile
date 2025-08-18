FROM amazoncorretto:17-alpine-jdk

# Gradle bootJar 산출물 경로(워크플로에서 --build-arg로 *.jar 전달)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app.jar

# Spring이 추가 위치에서 yml 읽도록(디렉터리 지정)
ENV SPRING_CONFIG_ADDITIONAL_LOCATION=file:/app/config/

# 프로필은 Compose에서 덮어씀(여기 기본값은 의미 없음)
ENV SPRING_PROFILES_ACTIVE=local
ENV SERVER_ENV=local

ENTRYPOINT ["java","-jar","/app.jar"]
