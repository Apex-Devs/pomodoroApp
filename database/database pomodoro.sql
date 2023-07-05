create database pomodoroapp;

use pomodoroapp;

create table cuentas(
correo varchar(30) primary key not null,
nombre varchar(30),
contraseña varchar(25)
);

create table tareas(
id_tarea int primary key not null auto_increment,
nombre_tarea varchar(30),
descripcion_tarea varchar(30),
numero_pomodoros int,
correo varchar(30),
foreign key(correo) references cuentas(correo) ON DELETE CASCADE ON UPDATE CASCADE
);

create table pomodoros(
id_pomodoro int primary key not null auto_increment,
tiempo_pomodoro time, 
tiempo_shortbreak time, 
tiempo_longbreak time, 
id_tarea int,
foreign key(id_tarea) references tareas(id_tarea) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into cuentas values('david-1020@live.com.mx', 'david', '1234');
insert into cuentas values('naruyu123@gmail.com', 'usan', 'ejemplo');

select * from cuentas;






