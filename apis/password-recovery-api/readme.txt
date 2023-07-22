password-recovery-api

Endpoints

GET: http://localhost:8080/password-recovery-api/passwordRecover

Json Required Example: 

{
  "email": "david-1020@live.com.mx"
}

POST: http://localhost:8080/password-recovery-api/{correo}/updatePassword

Json Required Example: 
{
  "password": "examplePassword1"
}
