package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SmsController {
    private final CertificationService certificationService;

    @PostMapping("/sendSMS")
    public ResponseEntity<? extends BasicResponse> sendSMS(String phoneNumber, String rand) {
        certificationService.certifiedPhoneNumber(phoneNumber, rand);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
