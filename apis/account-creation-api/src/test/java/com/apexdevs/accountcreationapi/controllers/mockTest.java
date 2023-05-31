package com.apexdevs.accountcreationapi.controllers;

import com.apexdevs.accountcreationapi.repository.AccountsRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.apexdevs.accountcreationapi.model.Accounts;

@ExtendWith(MockitoExtension.class)
public class mockTest {
    @Mock
    private AccountsRepository accountRepository;

    @Test
    void saveAccount(){
        Accounts account = new Accounts();
        //account.setId_usuario(1); //Opcional
        account.setNombre("David");
        account.setCorreo("123@gmail.com");
        account.setContraseña("123");

        Accounts savedAccount = accountRepository.save(account);
        Assert.assertEquals("David", account.getNombre());
    }
}
