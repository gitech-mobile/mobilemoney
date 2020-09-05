FROM adoptopenjdk/openjdk13:alpine-jre
VOLUME /tmp
COPY target/*?.?.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
