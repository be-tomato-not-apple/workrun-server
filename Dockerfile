FROM amazoncorretto:17-alpine-jdk

# Gradle bootJar 산출물 경로
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} /app.jar

# 런타임에 프로필/환경 주입 (기본값)
ENV SPRING_PROFILES_ACTIVE=local
ENV SERVER_ENV=local

# ENV 치환 보장
ENTRYPOINT ["sh","-c","java -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -Dserver.env=$SERVER_ENV -jar /app.jar"]