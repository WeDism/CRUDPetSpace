[![Build Status](https://travis-ci.org/WeDism/CRUDPetSpace.svg?branch=master)](https://travis-ci.org/WeDism/PetSpace) 
[![codecov](https://codecov.io/gh/WeDism/CRUDPetSpace/branch/master/graph/badge.svg)](https://codecov.io/gh/WeDism/PetSpace)

# This is a social network for pets. You can click for test use next url: [CRUDPetSpace](https://crud-pet-space.herokuapp.com) 
## For run this app you have to do next steps
1. install java 9
1. install postgres 10
1. install tomcat 8.5
1. [Download](https://github.com/WeDism/CRUDPetSpace/releases) latest war file from releases
1. deploy CRUDPetSpace-1.0.war by tomcat 8.5
1. cd $YOUR_PATH\apache-tomcat-8.5.24\webapps\CRUDPetSpace-1.0\WEB-INF\classes
1. find schema.sql
1. uncomment CREATE DATABASE pet_space; in schema.sql file and run sql script
