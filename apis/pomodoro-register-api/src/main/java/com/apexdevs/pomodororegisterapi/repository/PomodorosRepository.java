package com.apexdevs.pomodororegisterapi.repository;


import com.apexdevs.pomodororegisterapi.model.Pomodoros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PomodorosRepository extends JpaRepository<Pomodoros,Integer> {
    List<Pomodoros> findByFktarea(Integer fktarea);

}
