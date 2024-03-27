FROM gradle:jdk21-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/shorturlapi-0.0.1-SNAPSHOT.jar /app/app.jar

USER 65534
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
