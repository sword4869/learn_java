FROM java:8-alpine
COPY ./sentinel-dashboard-1.8.1.jar /tmp/app.jar
ENTRYPOINT java -Dserver.port=8090 -jar /tmp/app.jar