package com.qualitychemicals.qciss.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Personal {
    @Id
    @GeneratedValue
    private int id;
    @NotBlank(message = "firstName cannot be null")
    @Size(min=2, message = "name must have at least two Characters")
    private String firstName;
    @NotBlank(message = "lastName cannot be null")
    @Size(min=2, message = "Last name must have at least two Characters")
    private String lastName;
    @NotNull
    @Size(min=14, max=14,message = "NIN must have 14 Characters")
    @Pattern(regexp="^[a-zA-Z0-9]+$", message = "invalid NIN")
    private String nin;
    @NotNull
    @Size(min=10, max=10)
    @Pattern(regexp="(^[0-9]{10})")
    private String contact;
    @NotBlank
    @Email
    private String email;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dob;
    @NotBlank
    private String gender;
    @NotBlank
    private String residence;
    @NotNull
    private String image;
}
