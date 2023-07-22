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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pomodoro")
    private Integer id_pomodoro;

    @Column(name="time_pomodoro")
    @JsonFormat(pattern = "mm:ss")
    private Time time_pomodoro;

    @Column(name="time_shortbreak")
    @JsonFormat(pattern = "mm:ss")
    private Time time_shortbreak;

    @Column(name="time_longbreak")
    @JsonFormat(pattern = "mm:ss")
    private Time time_longbreak;

    @Column(name="fktask")
    private Integer fktask;

}
