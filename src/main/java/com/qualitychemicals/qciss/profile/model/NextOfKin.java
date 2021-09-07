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
public class NextOfKin {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String contact;
    private String relationship;
}
