FROM openjdk:17-jdk-alpine
WORKDIR app
COPY . .
RUN ./gradlew bootJar && cp build/libs/statistics-service-0.0.1-SNAPSHOT.jar ./monitor-sensors-statistic.jar
ENTRYPOINT ["java", "-jar", "monitor-sensors-statistic.jar"]