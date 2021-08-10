package com.thrive.agric.medicaldiagnosis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Specialisation {
    @Id
    @Column
    long id;
    @Column
    String name;
    @Column
    int specialistId;

}
