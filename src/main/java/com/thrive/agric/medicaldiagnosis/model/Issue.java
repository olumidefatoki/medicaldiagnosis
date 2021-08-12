package com.thrive.agric.medicaldiagnosis.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Issue {
    @Column
    @Id
    long id;
    @Column
    String name;
    @Column
    int ranking;
    @Column
    String symptomName;
    @Column
    String status;
    @Column
    int accuracy;
    @Column
    String icd;
    @Column
    String icdName;
    @Column
    String profName;


}
