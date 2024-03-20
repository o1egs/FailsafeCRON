FROM maven:3.6.3-openjdk-17
COPY ./ ./
RUN mvn clean package -Dmaven.test.skip=true
CMD ["java", "-jar", "target/FailsafeCRON-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
EXPOSE 8081