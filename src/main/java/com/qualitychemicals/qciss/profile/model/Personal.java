package com.qualitychemicals.qciss.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Personal {
    @Id
    @GeneratedValue
    private int id;
    @NotNull
    @Size(min=2, message = "name at least two Characters")
    private String firstName;
    @NotNull
    @Size(min=2, message = "Last name at least two Characters")
    private String lastName;
    @NotNull
    @Size(min=14, max=14,message = "NIN should have 15 Characters")
    private String NIN;
    @NotNull
    @Size(min=10, max=10)
    @Pattern(regexp="(^[0-9]{10})")
    private String contact;
    @NotNull
    @Email
    private String email;
    @NotNull
    private Date DOB;
    @NotNull
    private String gender;
    @NotNull
    private String residence;
}
