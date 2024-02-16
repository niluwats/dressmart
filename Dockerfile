FROM openjdk:17-jdk
WORKDIR /app

EXPOSE 8080
ARG APP_NAME="dressmart"
ARG APP_VERSION="0.0.1-SNAPSHOT"
ARG JAR_FILE="/build/libs/${APP_NAME}-${APP_VERSION}.jar"

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]