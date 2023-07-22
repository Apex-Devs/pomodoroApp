create database pomodoroapp;

use pomodoroapp;

create table accounts(
email varchar(30) primary key not null,
name varchar(30),
password varchar(25)
);

create table tasks(
id_task int primary key not null auto_increment,
task_name varchar(60),
task_description varchar(300),
pomodoro_quantity int,
email varchar(30),
foreign key(email) references accounts(email) ON DELETE CASCADE ON UPDATE CASCADE
);

create table pomodoros(
id_pomodoro int primary key not null auto_increment,
time_pomodoro time, 
time_shortbreak time, 
time_longbreak time, 
fktask int,
foreign key(fktask) references tasks(id_task) ON DELETE CASCADE ON UPDATE CASCADE
);







