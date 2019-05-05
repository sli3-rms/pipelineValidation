FROM java:8
#FROM ubuntu:16.04
#FROM builder 

RUN mkdir -p /opt/rest-service

COPY ./target/*.jar /opt/rest-service/

EXPOSE 8000

WORKDIR /opt/rest-service

CMD exec java -jar rest-service-1.0-SNAPSHOT.jar