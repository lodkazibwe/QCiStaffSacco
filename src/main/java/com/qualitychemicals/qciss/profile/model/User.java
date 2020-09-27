package com.qualitychemicals.qciss.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
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
    @OneToMany(targetEntity = Account.class,cascade= CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(columnDefinition = "accountId")
    private List<Account> account;
    @OneToOne(targetEntity = Summary.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private Summary summary;
    private String status;
    @OneToMany(targetEntity = Role.class,cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn()
    private Set<Role> role;


}
