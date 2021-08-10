package com.thrive.agric.medicaldiagnosis.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpecialisationData extends BaseModel{
    @JsonProperty("SpecialistID")
    int specialistID;
}
