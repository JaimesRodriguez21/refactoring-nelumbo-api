package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.EmailRequest;
import com.nelumbo.api.dto.response.SuccesfullResponse;
import com.nelumbo.api.service.ApiService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@Service
public class ApiServiceImpl implements ApiService {


    private final RestTemplate restTemplate;

    public ApiServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public SuccesfullResponse enviarEmail(EmailRequest emailRequest) {
        String apiUrl = "http://localhost:8081/api/mensajeria/send-mensaje";
        HttpHeaders headers = new HttpHeaders();
        //headers.set("Authorization", "Bearer YOUR_ACCESS_TOKEN");
        HttpEntity<EmailRequest> httpEntity = new HttpEntity<>(emailRequest, headers);
        ResponseEntity<SuccesfullResponse> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, SuccesfullResponse.class);
        SuccesfullResponse response = responseEntity.getBody();

        return response;
    }

}
