package com.nelumbo.api.controllers;

import com.nelumbo.api.annotations.IngressAllowed;
import com.nelumbo.api.dto.request.EmailRequest;
import com.nelumbo.api.dto.response.Succesfull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensajeria")
public class EmailController {
    @IngressAllowed({"ADMIN"})
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public Succesfull sendEmail(EmailRequest emailRequest) {
        return new Succesfull("Succefull");
    }
}
