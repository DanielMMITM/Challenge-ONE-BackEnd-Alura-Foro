# Challenge ONE | Back End | Forum
This API was a challenge from the Oracle Next Education Program, there was some "tasks / features" to implement to complete the challenge. However, I decided to take the challenge to the next level and include more features like: comment in a post and select a solution for your post, CRUD operations on a course model and comment model. Furthermore, I decided to do a fullstack forum, so you can find the front-end code of this project.

Visit the Front-end of this project here:[Forum-Front](https://github.com/DanielMMITM/forum-front)

For the database of this project I'm using MySQL. Here are some packages I'm using inside this project: *Lombok*, *Flyway*, *Spring-devtools*, *Spring-security*, *io.jsonwebtoken*.
## Documentation
The API documentation is handled by Swagger and I included all the information to let the people know everything about the endpoints.
**Note:** Maybe there are some things I haven't updated in the documentation like data responses, error responses, status codes, etc. I'm pretty sure there are just a couple of.

## Custom errors
For this API I decided to implement a exception handler class to send custom errors through the request body response.

## Authorization
The API uses JWT tokens to manage the authorization of the users.

## Things to get better
* Error / exception handler responses

## Things to do: 
⁉️ Implement some tests with JUnit or Mockito
