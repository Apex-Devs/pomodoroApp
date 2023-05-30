package com.apexdevs.accountcreationapi.repository;

import com.apexdevs.accountcreationapi.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts,Integer> {


}
