package com.apexdevs.taskregisterapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_task")
    private Integer id_task;

    @Column(name="task_name")
    private String task_name;

    @Column(name="task_description")
    private String task_description;

    @Column(name="pomodoro_quantity")
    private Integer pomodoro_quantity;

    @Column(name="email")
    private String email;



}
