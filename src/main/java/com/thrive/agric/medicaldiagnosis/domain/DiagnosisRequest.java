package com.thrive.agric.medicaldiagnosis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiagnosisRequest  extends BaseModel{

    @JsonProperty("Accuracy")
    int accuracy;
    @JsonProperty("Icd")
    String icd;
    @JsonProperty("IcdName")
    String icdName;
    @JsonProperty("ProfName")
    String profName;
    @JsonProperty("Ranking")
    int ranking;
    String symptomName;
    String status;

}
