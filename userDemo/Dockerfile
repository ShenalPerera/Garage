FROM openjdk:17-jdk-slim AS build

RUN mkdir -p /app

COPY ./ /app

WORKDIR /app

RUN ./gradlew clean build


EXPOSE 8000

CMD ["java", "-jar", "build/libs/userDemo-0.0.1-SNAPSHOT.jar"]