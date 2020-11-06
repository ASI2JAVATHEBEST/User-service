package com.cpe.springboot.http;

import com.cpe.springboot.card.model.CardModel;
import com.cpe.springboot.user.model.UserModel;
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

    public UserModel getUserById(final Integer id) {
        RestTemplate restTemplate = new RestTemplate();

        final String url = "http://localhost:8084/user/"+id;
        ResponseEntity<UserModel> responseEntity = restTemplate.getForEntity(url, UserModel.class);
        return responseEntity.getBody();
    }

    public void updateUser(UserModel user, Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserModel> request = new HttpEntity<>(user);

        final String url = "http://localhost:8084/user/"+id;
        restTemplate.exchange(url, HttpMethod.PUT, request, UserModel.class);
    }

    public void updateCard(CardModel card, Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CardModel> request = new HttpEntity<>(card);

        final String url = "http://localhost:8082/card/"+id;
        restTemplate.exchange(url, HttpMethod.PUT, request, CardModel.class);
    }

}
