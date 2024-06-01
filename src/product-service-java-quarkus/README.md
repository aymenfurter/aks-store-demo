Run: ./mvnw compile quarkus:dev
Build: ./mvnw package
Run: java -jar target/quarkus-app/quarkus-run.jar
Containerize: docker build -f src/main/docker/Dockerfile.jvm .
Run Container:  docker run -it -p 8080:8080 product-service