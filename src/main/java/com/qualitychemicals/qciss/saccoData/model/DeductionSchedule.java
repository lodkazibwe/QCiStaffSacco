package com.qualitychemicals.qciss.saccoData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeductionSchedule {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne(targetEntity = ScheduleEmployee.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private ScheduleEmployee employee;
    @OneToMany(targetEntity = ScheduleLoan.class,cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn()
    List<ScheduleLoan> dueLoans;
    private double total;
}
