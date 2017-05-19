FROM 'frolvlad/alpine-oraclejdk8:slim'
WORKDIR '/awebbs'
COPY build/libs/awebbs.jar awebbs.jar
ENTRYPOINT ["java", "-jar", "awebbs.jar"]
EXPOSE 8080 8080
