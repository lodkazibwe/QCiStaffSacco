package com.qualitychemicals.qciss.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserFile {
    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private String fileName;
    private String filePath;
    @OneToOne(targetEntity = FileCat.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private FileCat category;

}
