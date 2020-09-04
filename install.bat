::Create BackEnd docker image:
mvn compile jib:dockerBuild
::Create Frond End Docker Image:
cd ij-storage
docker build -f .\Dockerfile -t ij-storage-vue:1.0 .
:: Create the database Image:
cd ../databaseService
docker build -t ij-storage-sql -f ./DockerfileDB .
:: Create ssl certificates
cd ..
mkcert ij-storage.io
mkdir nginx\certs
move ij-storage.io.pem ./nginx/certs/ij-storage.io.crt
move ij-storage.io-key.pem ./nginx/certs/ij-storage.io.key
