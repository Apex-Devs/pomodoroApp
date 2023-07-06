package com.apexdevs.passwordrecoveryapi.repository;


import com.apexdevs.passwordrecoveryapi.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts,String> {


}
