package com.qualitychemicals.qciss.profile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private int id;
    @NotBlank(message = "first name cannot be blank")
    @Size(min=2, message = "name at least two Characters")
    private String firstName;
    @NotBlank(message = "last name cannot be blank")
    @Size(min=2, message = "Last name at least two Characters")
    private String lastName;
    @NotNull
    @Size(min=14, max=14,message = "NIN must have 14 Characters")
    @Pattern(regexp="^[a-zA-Z0-9]+$", message = "invalid NIN")
    private String nin;
    @NotNull
    @Size(min=10, max=10, message = "invalid Contact length")
    @Pattern(regexp="(^[0-9]{10})", message = "invalid Contact")
    private String contact;
    @NotBlank(message="email cannot be blank")
    @Email(message="invalid Email")
    private String email;
    @NotNull(message="invalid date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dob;
    @NotBlank(message ="Gender cannot be Blank")
    private String gender;
    @NotBlank(message ="residence cannot be Blank")
    private String residence;

}
