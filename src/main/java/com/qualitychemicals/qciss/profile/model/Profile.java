package com.qualitychemicals.qciss.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    @NotNull
    @Valid
    private User user;
    @OneToOne(targetEntity = Personal.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    @NotNull
    @Valid
    private Personal personal;
    @OneToOne(targetEntity = Work.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private Work work;
    @OneToMany(targetEntity = Account.class,cascade= CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(columnDefinition = "accountId")
    private List<Account> account;
    @OneToOne(targetEntity = Summary.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private Summary summary;


}
