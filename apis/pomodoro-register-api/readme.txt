pomodoro-register-api

Endpoints

POST: http://localhost:8080/pomodoro-register-api/addPomodoro

Json Required Example: 

{
  "time_pomodoro": "00:50:00",
  "time_shortbreak": "00:10:00",
  "time_longbreak": "00:30:00",
  "fktask": "2"
}

GET: http://localhost:8080/pomodoro-register-api/listPomodoros
This endpoint doesn't required a body