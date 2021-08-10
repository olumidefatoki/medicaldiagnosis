package com.thrive.agric.medicaldiagnosis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diagnosis {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    String symptomName;
    @Column
    String status;

    @ManyToOne
    @JoinColumn(name="issueId", nullable=false)
    private Issue issue;

}
