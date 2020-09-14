package com.qualitychemicals.qciss.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column(unique=true)
    @NotNull
    @Size(min=3, message = "user name at least three Characters")
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String status;
    @OneToMany(targetEntity = Role.class,cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn()
    @NotNull
    private List<Role> role;
}
