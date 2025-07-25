package com.prep_saga.PrepSaga.entity.topic;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // e.g., "System Design"

    @Column(columnDefinition = "TEXT")
    private String content; // Rich textual content

    @Column(length = 500)
    private String summary; // Short description for tile view

}
