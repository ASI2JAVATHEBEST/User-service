package com.cpe.springboot.http;

import com.cpe.springboot.card.model.CardModel;
import com.cpe.springboot.user.model.UserEntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

    public CardModel getCardById(final Integer id) {
        RestTemplate restTemplate = new RestTemplate();

        final String url = "http://localhost:8082/card/"+id;
        ResponseEntity<CardModel> responseEntity = restTemplate.getForEntity(url, CardModel.class);
        return responseEntity.getBody();
    }

    public UserEntityModel getUserById(final Integer id) {
        RestTemplate restTemplate = new RestTemplate();

        final String url = "http://localhost:8084/user/"+id;
        ResponseEntity<UserEntityModel> responseEntity = restTemplate.getForEntity(url, UserEntityModel.class);
        return responseEntity.getBody();
    }

    public void updateUser(UserEntityModel user, Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserEntityModel> request = new HttpEntity<>(user);

        final String url = "http://localhost:8084/user/"+id;
        restTemplate.exchange(url, HttpMethod.PUT, request, UserEntityModel.class);
    }

    public void updateCard(CardModel card, Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CardModel> request = new HttpEntity<>(card);

        final String url = "http://localhost:8082/card/"+id;
        restTemplate.exchange(url, HttpMethod.PUT, request, CardModel.class);
    }

}
