package com.qualitychemicals.qciss.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Work {
    @Id
    @GeneratedValue
    private int id;
    private String company;
    private String job;
    private String scale;
    private int monthlySaving;
}
