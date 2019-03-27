FROM java:8

RUN mkdir -p /opt/rest-service

COPY ./target/*.jar /opt/rest-service/

EXPOSE 8000

WORKDIR /opt/rest-service

CMD exec java -jar rest-service-1.0-SNAPSHOT.jar