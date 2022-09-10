# web_server

A REST service that accepts HTTP requests and returns a response in the form of JSON
There are 5 controllers in the application, each responsible for different functions:

UserController - POST method for login to users
ProfileRegistrationController - POST method for user registration
ProfileController - GET method for user information output
FoldersController - GET, POST, DELETE methods for working with user folders
TaskController - GET, POST, DELETE methods for working with user tasks inside folders


For example, when a user makes a request, the service handle a POST request for /login 1, if the password hash matches and the username matches, 
then the service returns the unique token to the user.

When the user makes a request, the service processes the GET request for /userprofile/main, receiving the @RequestHeader("Authorization") header 
and returns the UserDTO object to the user in the form of JSON

{
"name": "Ivan",
"lastname": "Ivanov",
"email":"11111@mail.ru",
"id":1
}


The database is connected, there are created tables: usertoken, usertable, userfolders, usertasks 
usertoken - storing userid and token
usertable - userid, information about the user during registration
userfolders - userid, folderid, folder title
usertask - userid, folderid, taskid, task title, description, task creation time, status (new, closed)
