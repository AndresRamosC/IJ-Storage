
# IJ-STORAGE APP
This is a full-stack developed app to be used as a local administration of stock.

## Prerequisites

- [mkcert](https://github.com/FiloSottile/mkcert#installation)
- [Docker Desktop](https://www.docker.com/products/docker-desktop)
- [IJ-Storage Repo](https://github.com/AndresRamosC/IJ-Storage)
## The setup
First we need to setup the IP Adress of the host that will be used as a server:
1. Open the next file: `ij-storage/src/service/componentService.js`
2. Edit line number 6: `host="192.168.1.68"; <- the ip Adress inside`

In this step we will create all the docker images that we will requiere
### Back End Service
1. Go to root folder of the repository and run: 

    ```
    mvn compile jib:dockerBuild
	```
### Front End App
1. Go to ij-storage sub folder and run:
	 ```
   docker build -f .\Dockerfile -t ij-storage-vue:1.0 .
	```
    

Use the Send Device Data function with two parameters

### SSL Certificates
1. Go to root folder of the repository and run: 
	 ```
    mkcert ij-storage.io
	```
 2. Once the certificates are created on the run folder run:
	 ```
    move ij-storage.io.pem ./nginx/certs/ij-storage.io.crt
	move ij-storage.io-key.pem ./nginx/certs/ij-storage.io.key
	```
 ### Data Base Service
 1. Go to folder databaseService and run: 
	 ```
    docker build -t ij-storage-sql -f ./DockerfileDB .
	```

## Automatic Setup
1. Go to root folder of the repository and run the install.bat
## The Deployment

1. Go to root folder of the repository and run: 

    ```
    docker-compose -f docker-compose.yml up -d
	```
2. As they are self signed certificates you should accept the authenticity of them accesing to the next pages:

	 ```
	 //Change example_url for your ip adress:
    https://example_url:9080/
    https://example_url/
	```
	Note: Do this on your pc and on your cell phone.