package com.apexdevs.taskregisterapi.repository;


import com.apexdevs.taskregisterapi.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks,Integer> {

    List<Tasks> findByEmail(String email);

}
