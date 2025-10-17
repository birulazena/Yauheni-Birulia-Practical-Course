package com.github.birulazena.user_service.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "card_info")
public class CardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    @ManyToOne()
    private User user;

    private String number;

    private String holder;

    @Column(name = "expiration_date")
    private Date expirationDate;

}
