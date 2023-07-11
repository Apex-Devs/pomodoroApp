package com.apexdevs.taskregisterapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tareas")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tarea")
    private Integer id_tarea;

    @Column(name="nombre_tarea")
    private String nombre_tarea;

    @Column(name="descripcion_tarea")
    private String descripcion_tarea;

    @Column(name="numero_pomodoros")
    private Integer numero_pomodoros;

    @Column(name="correo")
    private String correo;



}
