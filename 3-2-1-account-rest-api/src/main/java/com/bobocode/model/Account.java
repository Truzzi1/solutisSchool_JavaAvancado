package com.bobocode.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account {

    // Getters and Setters
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private double balance;

    // Metodo para criar uma conta
    public static Account create(String firstName, String lastName, String email) {
        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        return account;
    }
}