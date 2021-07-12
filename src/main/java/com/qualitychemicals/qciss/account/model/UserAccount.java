package com.qualitychemicals.qciss.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn()
public class UserAccount {
    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private String name;
    @Column(unique=true)
    private String accountRef;
    private double amount;
    private int lastTransaction;
}
