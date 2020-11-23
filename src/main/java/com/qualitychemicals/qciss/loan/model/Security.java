package com.qualitychemicals.qciss.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Security {
    @Id
    @GeneratedValue
    private int id;
    private String description;
    private String guarantor;
    private String file;
}
