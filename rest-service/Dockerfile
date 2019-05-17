FROM java:8
#FROM ubuntu:16.04
#FROM builder 

ARG artifact

RUN mkdir -p /opt/rest-service

COPY ./target/*.jar /opt/rest-service/

EXPOSE 8000

WORKDIR /opt/rest-service

CMD exec java -jar ${artifact}