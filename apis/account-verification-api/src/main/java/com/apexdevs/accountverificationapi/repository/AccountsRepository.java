package com.apexdevs.accountverificationapi.repository;


import com.apexdevs.accountverificationapi.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts,Integer> {


}
