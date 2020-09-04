--# create databases
CREATE DATABASE IF NOT EXISTS `ijstorage`;
--# create root user and grant rights
--# CREATE USER 'root'@'localhost' IDENTIFIED BY 'local';
CREATE USER 'ijstorageuser'@'%' IDENTIFIED BY 'ijstoragepass';
GRANT ALL ON *.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON ijstorage . * TO 'ijstorageuser'@'%';
FLUSH PRIVILEGES;