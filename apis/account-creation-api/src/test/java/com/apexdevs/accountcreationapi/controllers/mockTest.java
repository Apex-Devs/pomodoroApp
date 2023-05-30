package com.apexdevs.accountcreationapi.controllers;

import com.apexdevs.accountcreationapi.model.AccountsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import com.apexdevs.accountcreationapi.model.Accounts;
import com.apexdevs.accountcreationapi.controllers.AccountsRepository;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
public class mockTest {
    /*@Autowired
    private Accounts accounts;*/
    @Mock
    private AccountsRepository accountRepository;

    @Test
    void saveAccount(){
        Accounts account = new Accounts();
        account.setNombre("David");
        account.setCorreo("123@gmail.com");
        account.setContraseña("123");

        Accounts savedAccount = accountRepository.save(account);

    }
}
