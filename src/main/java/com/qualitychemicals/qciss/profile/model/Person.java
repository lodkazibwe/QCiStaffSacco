package com.qualitychemicals.qciss.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private String nin;
    @Column(unique = true)
    private String mobile;
    @Column(unique = true)
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dob;
    private String gender;
    private String residence;
    private String image;
}
