
# Akbank bootcamp final project

## Weather-API

This project, is an API that gives weather data and proceeds User GET, POST, PUT and DELETE request.

---
# Setting up project
## Maven setup `(ignore if already setup)`:

        a. Install maven from https://maven.apache.org/download.cgi

        b. Unzip maven and keep in C drive (you can keep any location. Path location will be changed accordingly).

        c. Set MAVEN_HOME in system variable.
---

## Build Spring Boot Project with Maven
    mvn package
## `or`

      mvn install / mvn clean install
## Run Spring Boot app using Maven:

     mvn spring-boot:run
## `[optional]` Run Spring Boot app with java -jar command

      java -jar target/mywebserviceapp-0.0.1-SNAPSHOT.jar

---
## Endpoints
---
 You can make API request via API test tools.( Postman or cURL):
- `GET api/v1.0/appuser`: Gets appuser.
  
- `DELETE api/v1.0/appuser`: Deletes appuser.
  
- `POST api/v1.0/appuser/savedcities`: Add new saved city to appuser.
  
- `DELETE api/v1.0/appuser/savedcities`: Delete saved city from appuser

- `POST api/v1.0/auth/user-registrations/app-user`: Register appuser.

- `POST api/v1.0/auth/user-registrations/authenticate`: Authenticate appuser

---
If you have any questions or feedback regarding the project, please feel free to reach out to me. You can contact me through the following:

`Email:` dikbykmert@gmail.com

`Twitter:` @mertdkb