package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rest.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.RequestEntity.put;

@Component
public class Controller {
    private final HttpHeaders httpHeaders;
    private final RestTemplate restTemplate;

    private String URL = "http://94.198.50.185:7081/api/users";

    @Autowired
    public Controller(HttpHeaders httpHeaders, RestTemplate restTemplate) {
        this.httpHeaders = httpHeaders;
        this.restTemplate = restTemplate;
        this.httpHeaders.set("Cookie",
                String.join(";", restTemplate.headForHeaders(URL).get("Set-Cookie")));
    }

    public String getAnswer() {
        return addUser().getBody() + updateUser().getBody() + deleteUser().getBody();
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange
                (URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                });
        return responseEntity.getBody();
    }

    private ResponseEntity<String> addUser() {
        User user = new User(1L, "Vladislav", "Nedobugin", (byte) 25);
        HttpEntity<User> http = new HttpEntity<>(user, httpHeaders);
        return restTemplate.postForEntity(URL, http, String.class);
    }

    private ResponseEntity<String> updateUser() {
        User user = new User(1L, "Alex", "Nedobugina", (byte) 24);
        HttpEntity<User> http = new HttpEntity<>(user, httpHeaders);
        return restTemplate.exchange(URL, HttpMethod.PUT, http, String.class, 1);
    }

    private ResponseEntity<String> deleteUser() {
        Map<String, Long> map = new HashMap<>();
        {
            put("id", 3);
        }
        HttpEntity<User> http = new HttpEntity<>(null, httpHeaders);
        return restTemplate.exchange(URL + "/{id}", HttpMethod.DELETE, http, String.class, map);
    }
}
