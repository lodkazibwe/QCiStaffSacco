package com.qualitychemicals.qciss.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotEmpty(message = "user name must not be empty")
    @Size(min=3, message = "user name at least three alphanumeric Characters")
    @Pattern(regexp="^[a-zA-Z0-9]+$", message = "User name must contain Only alphanumeric Characters")
    private String userName;
    @NotEmpty
    @Size(min=6, message = "PassWord Should have At Least six characters")
    private String password;
    @NotNull(message = "invalid status")
    private String status;
    @OneToMany(targetEntity = Role.class,cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn()
    @NotNull(message = "No role")
    private Set<Role> role;
}
