package com.nelumbo.api.controllers;

import com.nelumbo.api.annotations.IngressAllowed;
import com.nelumbo.api.dto.request.EmailRequest;
import com.nelumbo.api.dto.response.SuccesfullResponse;
import com.nelumbo.api.service.ApiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/mensajeria")
public class EmailController {

    @Autowired
    ApiService apiService;

    @IngressAllowed({"ADMIN"})
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public SuccesfullResponse sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        return apiService.enviarEmail(emailRequest);
    }
}
