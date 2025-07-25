package com.prep_saga.PrepSaga.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // Getters and Setters
}
