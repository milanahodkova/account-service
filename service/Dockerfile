FROM eclipse-temurin:21-jre-alpine

ADD ./build/libs/account-service-*.jar /app/account-service.jar

ENTRYPOINT ["java", git add "-jar", "/app/account-service.jar"]