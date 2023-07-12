package com.apexdevs.pomodororegisterapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.*;
import java.sql.Time;

@Data
@Entity
@Table(name = "pomodoros")
public class Pomodoros {

    @Id
    @Column(name="id_pomodoro")
    private Integer id_pomodoro;

    @Column(name="tiempo_pomodoro")
    @JsonFormat(pattern = "mm:ss")
    private Time tiempo_pomodoro;

    @Column(name="tiempo_shortbreak")
    @JsonFormat(pattern = "mm:ss")
    private Time tiempo_shortbreak;

    @Column(name="tiempo_longbreak")
    @JsonFormat(pattern = "mm:ss")
    private Time tiempo_longbreak;

    @Column(name="id_tarea")
    private Integer id_tarea;

}
