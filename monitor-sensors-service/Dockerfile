FROM openjdk:17-jdk-slim
WORKDIR app
COPY . .
RUN ./gradlew bootJar && cp build/libs/*.jar ./monitor-sensors.jar
ENTRYPOINT ["java", "-jar", "monitor-sensors.jar"]