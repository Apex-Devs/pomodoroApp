task-register-api

Endpoints

POST: http://localhost:8080/task-register-api/addTask

Json Required Example: 
{
  "task_name": "play a game",
  "task_description": "complete a game",
  "pomodoro_quantity": "2",
  "email": "david-1020@live.com.mx"
}

GET: http://localhost:8080/task-register-api/listTask

Json Required Example: 

{
  "email": "david-1020@live.com.mx"
}
