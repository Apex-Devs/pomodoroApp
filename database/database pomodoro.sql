create database pomodoroapp;

use pomodoroapp;

create table cuentas(
correo varchar(30) primary key not null,
nombre varchar(30),
contraseña varchar(25)
);

create table tareas(
id_tarea int primary key not null auto_increment,
nombre_tarea varchar(60),
descripcion_tarea varchar(300),
numero_pomodoros int,
correo varchar(30),
foreign key(correo) references cuentas(correo) ON DELETE CASCADE ON UPDATE CASCADE
);

create table pomodoros(
id_pomodoro int primary key not null auto_increment,
tiempo_pomodoro time, 
tiempo_shortbreak time, 
tiempo_longbreak time, 
fktarea int,
foreign key(fktarea) references tareas(id_tarea) ON DELETE CASCADE ON UPDATE CASCADE
);








