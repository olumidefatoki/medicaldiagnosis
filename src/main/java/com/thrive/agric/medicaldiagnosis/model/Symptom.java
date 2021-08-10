package com.thrive.agric.medicaldiagnosis.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Symptom {
    @Id
    @Column
    long id;
    @Column
    String name;
}
