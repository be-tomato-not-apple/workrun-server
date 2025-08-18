FROM amazoncorretto:17-alpine-jdk

# Gradle bootJar 산출물 경로(워크플로에서 --build-arg로 *.jar 넘김)
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} /app.jar

# Spring이 직접 읽게 둘 환경변수(기본값). compose에서 덮어씁니다.
ENV SPRING_PROFILES_ACTIVE=local
ENV SERVER_ENV=local
ENV SPRING_CONFIG_ADDITIONAL_LOCATION=file:/app/config/

# 더 이상 sh -c 사용하지 않음 → 단어분리 문제 사라짐
ENTRYPOINT ["java","-jar","/app.jar"]