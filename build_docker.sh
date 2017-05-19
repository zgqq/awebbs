./gradlew clean build
docker rmi awebbs --force
docker build ../awebbs
docker tag "$(docker images -f 'dangling=true' -q)" awebbs
docker rm awebbs
docker run --name=awebbs --publish 8080:8080 awebbs:latest
