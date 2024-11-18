FROM amazoncorretto:11.0.22-alpine3.19

COPY target/*.jar app.jar
EXPOSE 8080

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir /mnt/apps
RUN chmod -R 755 /mnt/apps

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=local"]
