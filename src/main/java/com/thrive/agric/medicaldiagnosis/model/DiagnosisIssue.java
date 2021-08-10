package com.thrive.agric.medicaldiagnosis.model;

import javax.persistence.Column;

public class DiagnosisIssue {
    String symptomName;
    long id;
    String name;
    int accuracy;
    String icd;
    String icdName;
    String profName;
    int ranking;
}
