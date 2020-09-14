package com.qualitychemicals.qciss.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    @NotNull
    private User user;
    @OneToOne
    @NotNull
    private Personal personal;
    @OneToOne
    private Work work;
    @OneToMany(targetEntity = Account.class,cascade= CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn()
    private List<Account> account;
    @OneToOne
    private Summary summary;


}
