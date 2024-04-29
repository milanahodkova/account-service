FROM eclipse-temurin:21-jre-alpine

ADD ./build/libs/account-service-*.jar /app/account-service.jar

ENTRYPOINT ["java", "-jar", "/app/account-service.jar"]