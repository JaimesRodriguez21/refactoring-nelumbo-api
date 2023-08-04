package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.EmailRequest;
import com.nelumbo.api.dto.response.SuccesfullResponse;
import org.springframework.stereotype.Service;

@Service
public interface ApiService {

    SuccesfullResponse enviarEmail(EmailRequest emailRequest);
}
