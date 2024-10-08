#
# Build stage
#

FROM openjdk:11-jdk

COPY target/spring_rest_docker.jar .

EXPOSE 9000

ENTRYPOINT ["java","-jar","spring_rest_docker.jar"]


