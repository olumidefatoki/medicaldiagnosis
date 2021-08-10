package com.thrive.agric.medicaldiagnosis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thrive.agric.medicaldiagnosis.domain.*;
import com.thrive.agric.medicaldiagnosis.model.Diagnosis;
import com.thrive.agric.medicaldiagnosis.model.Issue;
import com.thrive.agric.medicaldiagnosis.model.Specialisation;
import com.thrive.agric.medicaldiagnosis.model.SpecilisationIssue;
import com.thrive.agric.medicaldiagnosis.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiagnosisService {
    private final ObjectMapper objectMapper;
    private final BaseRepository repo;
    private final ModelMapper mapper;


    public void postData(DiagnosisRequest request) {

        Issue issue = mapper.map(request, Issue.class);
        log.info("request {}", request);
        boolean present = repo.findOneOptional(Issue.class, issue.getId()).isPresent();
        if (!present) {
            repo.save(issue);
        }
        Diagnosis diagnosis = Diagnosis.builder().issue(issue).symptomName(request.getSymptomName()).status(request.getStatus()).build();
        present = repo.findOneOptional(Diagnosis.class, diagnosis.getId()).isPresent();
        if (!present) {
            repo.save(diagnosis);
        }

    }

    public void saveSpecilisationData(SpecialisationRequest request) {
        log.info("{}", request);
        request.getSpecialisations().forEach(data -> {
            boolean present = repo.findOneOptional(Specialisation.class, data.getId()).isPresent();
            if (!present) {
                Specialisation specialisation = mapper.map(data, Specialisation.class);
                log.info("specialisation:{}", specialisation);
                repo.save(specialisation);
            }
            SpecilisationIssue specilisationIssue = SpecilisationIssue.builder().issueId(request.getIssueId()).specilisationId(data.getId()).build();
            repo.save(specilisationIssue);
        });
    }

    public Pagination<DiagnosisResponse> getAllDiagnosis(SearchRequest request) {
        Map<String, Object> filter = objectMapper.convertValue(request, Map.class);
        log.info("filter {}" , filter );
        PaginationRequest page = PaginationRequest.builder().page(request.getPage()).size(request.getSize()).build();
        Page<Diagnosis> response = repo.findAllBy(Diagnosis.class, filter, page);
        return mapper.map(response, new TypeToken<Pagination<Diagnosis>>() {
        }.getType());
    }
}
