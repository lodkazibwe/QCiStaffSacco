package com.qualitychemicals.qciss.saccoData.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeductionSchedule {
    @Id
    @GeneratedValue
    private int id;
    private String company;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date date;
    private String key;
    private ScheduleStatus status;
    @OneToOne(targetEntity = ScheduleEmployee.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private ScheduleEmployee employee;
    @OneToMany(targetEntity = ScheduleLoan.class,cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn()
    List<ScheduleLoan> dueLoans;
    private double total;
}
