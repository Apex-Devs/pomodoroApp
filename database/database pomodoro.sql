create database pomodoroapp;

use pomodoroapp;

create table cuentas(
id_usuario int primary key not null auto_increment,
nombre varchar(30),
correo varchar(25),
contraseña varchar(25)
);

create table tareas(
id_tarea int primary key not null auto_increment,
nombre_tarea varchar(30),
descripcion_tarea varchar(30),
numero_pomodoros int
);

create table pomodoros(
id_pomodoro int primary key not null auto_increment,
tiempo_pomodoro time, 
tiempo_shortbreak time, 
tiempo_longbreak time, 
id_tarea int,
foreign key(id_tarea) references tareas(id_tarea) ON DELETE CASCADE ON UPDATE CASCADE
);


