FROM openjdk:17-jdk-alpine
WORKDIR app
COPY . .
RUN ./gradlew bootJar && cp build/libs/*.jar ./monitor-sensors.jar
ENTRYPOINT ["java", "-jar", "monitor-sensors.jar"]