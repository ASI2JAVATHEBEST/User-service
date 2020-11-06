package com.cpe.springboot.http;

import com.cpe.springboot.card.model.CardModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpClient {

    public HttpClient() {

    }

    public List<CardModel> getRandCards() {
        RestTemplate restTemplate = new RestTemplate();

        final String url = "http://localhost:8082/cards_rand";
        ResponseEntity<CardModel[]> responseEntity = restTemplate.getForEntity(url, CardModel[].class);
        return Arrays.stream(responseEntity.getBody()).collect(Collectors.toList());
    }

}
