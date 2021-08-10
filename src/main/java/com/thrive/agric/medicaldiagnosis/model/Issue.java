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
    int accuracy;
    @Column
    String icd;
    @Column
    String icdName;
    @Column
    String profName;
    @Column
    int ranking;
}
