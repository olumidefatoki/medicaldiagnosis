package com.thrive.agric.medicaldiagnosis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagination<T> {

    private List<T> content;
    private int number;
    private int size;

    public Pagination(Page<T> results) {
        this.content = results.getContent();
        this.size = results.getSize();
        this.number =results.getNumber();
    }
}
