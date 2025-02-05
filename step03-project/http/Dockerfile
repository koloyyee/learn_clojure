# Use Java base image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/webapp.jar app.jar

EXPOSE 3000

ENTRYPOINT [ "java", "-jar", "app.jar", "3000" ]