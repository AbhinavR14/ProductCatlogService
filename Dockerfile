FROM openjdk

COPY target/ProductCatlogService-0.0.1-SNAPSHOT app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
