package com.thrive.agric.medicaldiagnosis.controller;

import com.thrive.agric.medicaldiagnosis.domain.*;
import com.thrive.agric.medicaldiagnosis.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diagnosis")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService service;

    @PostMapping("/issue")
    public ResponseEntity<ApiResponse> postDiagnosis(@RequestBody DiagnosisRequest request) {
        service.postData(request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message("successful")
                .status(HttpStatus.OK.value())
                .data("successful")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/specilisation")
    public ResponseEntity<ApiResponse> postSpeciliation(@RequestBody SpecialisationRequest request) {
        service.saveSpecilisationData(request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message("successful")
                .status(HttpStatus.OK.value())
                .data("successful")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/report")
    public ResponseEntity<ApiResponse> getAllDiagnosis(SearchRequest request) {
        Pagination<DiagnosisResponse> allDiagnosis = service.getAllDiagnosis(request);
        ApiResponse<Pagination> response = ApiResponse.<Pagination>builder().message("Diagnosis list.")
                .status(HttpStatus.OK.value()).data(allDiagnosis).build();
         ResponseEntity.ok().body(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
