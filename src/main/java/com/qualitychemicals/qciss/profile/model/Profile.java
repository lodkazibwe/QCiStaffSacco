package com.qualitychemicals.qciss.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue
    private int id;
    @Column(unique=true)
    private String userName;
    private String password;

    @OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private Person person;
    @OneToOne(targetEntity = Work.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private Work work;
    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private Account account;
    @OneToOne(targetEntity = Bank.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private Bank bank;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(targetEntity = Role.class,cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn()
    private Set<Role> role;


}
