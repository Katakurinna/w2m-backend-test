#### Build Image

FROM maven:3.8.5-openjdk-17 as builder

WORKDIR /build
COPY src src
COPY pom.xml pom.xml

RUN mvn -f pom.xml clean package

#### Final Image

FROM openjdk:17-jdk-alpine
COPY --from=builder /build/target/spaceship.*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]