package com.thrive.agric.medicaldiagnosis.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BaseModel {
    @JsonProperty("ID")
    long id;
    @JsonProperty("Name")
    String name;
}
