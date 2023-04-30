package com.example.resttemplate.service;


import com.example.resttemplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final User user;
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders requestHeaders = new HttpHeaders();

    @Value("${api.address}")
    private String apiURL;

    @Autowired
    UserServiceImpl(User user){
        this.user = user;
    }

    public String completeOperations(){
        findAllUsers();
        return createUser()+updateUser()+deleteUser();
    }

    @Override
    public void findAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(apiURL, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() { });
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        String sessionId = httpHeaders.getFirst("Set-Cookie");
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Cookie", sessionId);
    }

    @Override
    public String createUser(){
        HttpEntity<User> httpEntity = new HttpEntity<>(user, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiURL, HttpMethod.POST, httpEntity, String.class);
        return responseEntity.getBody();
    }

    @Override
    public String updateUser(){
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> httpEntity = new HttpEntity<>(user, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiURL, HttpMethod.PUT, httpEntity, String.class);
        return responseEntity.getBody();
    }

    @Override
    public String deleteUser(){
        HttpEntity<User> httpEntity = new HttpEntity<>(user, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiURL + "/" + user.getId(), HttpMethod.DELETE, httpEntity, String.class);
        return responseEntity.getBody();
    }
}
