package com.prep_saga.PrepSaga.entity;


import com.prep_saga.PrepSaga.model.Role;
import com.prep_saga.PrepSaga.model.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // "ADMIN" or "USER"

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    public String getUserName() {
        return "Mr/Mrs " + firstName + " " + lastName;
    }
}
