package com.thrive.agric.medicaldiagnosis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecialisationRequest extends BaseModel {
    List<SpecialisationData> specialisations = new ArrayList();
    long issueId;
}
