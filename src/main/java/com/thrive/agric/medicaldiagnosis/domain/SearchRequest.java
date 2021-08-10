package com.thrive.agric.medicaldiagnosis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequest {

    String status;

    @Builder.Default
    @JsonIgnore
    private Integer page = 1;

    @Builder.Default
    @JsonIgnore
    private Integer size = 25;
}
