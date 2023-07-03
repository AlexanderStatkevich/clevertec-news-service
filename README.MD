# news-project

Test task for Clevertec

This service can be used as news and comments handling system. Also supported user management.

## Startup

To start application you need to clone repository on your local machine. Install docker environment and run
docker-compose file.

## Endpoints

### news-service

### /news

To get a page of news:

 ```sh
 (GET)http://host:port/api/v1/news
  ```

To get a page of filtered news need to pass filter object with desired parameters like array of conditions:

 ```sh
 (GET)http://host:port/api/v1/news/filter?filter={"conditions": [{"comparison": "LIKE","value": "first", "field": "title"}]}
  ```

To get news by id:

 ```sh
 (GET)http://host:port/api/v1/news/{id}
  ```

To create:

 ```sh
 Send POST request on http://host:port/api/vi/news
 
 And pass following in the body of request in JSON format:
{
    "title": "title example",
    "text": "text example"
}
  ```

To update:

 ```sh
 Send PATCH request on http://host:port/api/vi/news/{id}
 
 And pass following in the body of request in JSON format:
 {
    "text":"text example",
    "title":"title example"
}
  ```

To delete:

 ```sh
 Send DELETE request on http://host:port/api/vi/news/{id}
  ```

### /comments

To get a page of comments:

 ```sh
 (GET)http://host:port/api/v1/comments
  ```

To get comment by id:

 ```sh
 (GET)http://host:port/api/v1/comments/{id}
  ```

To create:

 ```sh
 Send POST request on http://host:port/api/vi/comments
 
 And pass following in the body of request in JSON format:
{
    "text": "text example",
    "newsId": 1
}
  ```

To update:

 ```sh
 Send PATCH request on http://host:port/api/vi/comments/{id}
 
 And pass following in the body of request in JSON format:
{
    "text": "text example"
}
  ```

To delete:

 ```sh
 Send DELETE request on http://host:port/api/vi/comments/{id}
  ```

### /users

Describing only client endpoints..

To register:

 ```sh
 (POST)http://host:port/api/v1/users/registration
 
 with body:
 {
        "mail":"example@example.com",
        "fio":"example examplovich",
        "role": "SUBSCRIBER"  {"SUBSCRIBER", "JOURNALIST"}
        "password":"most_secured_pass"
 }
  ```

After registration, you'll get verification code on email.
For verification, you need to enter code like below:

 ```sh
 (GET)http://host:port/api/v1/users/verification?mail=example@example.com&code=example
  ```

Log in :

 ```sh
 (POST)http://host:port/api/v1/users/login
 
  with body:
 {
        "mail": "example@example.com",
        "password": "most_secured_pass"
 }
  ```

After log in you'll get JSON Web Token for authorization on another secured endpoints.

Access to your info:

 ```sh
 (GET)http://host:port/api/v1/users/me
  ```




