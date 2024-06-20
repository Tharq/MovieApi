package com.bharani.movie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class ForgotPassword {

    @Id
    @GeneratedValue
    private Integer fId;

    private Integer otp;

    private Date ExpirationDate;

    @OneToOne
    private User user;
}
