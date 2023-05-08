FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar emdb.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","/emdb.jar"]