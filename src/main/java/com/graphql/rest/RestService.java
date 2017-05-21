package com.graphql.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestService {

  private static final Logger log = LoggerFactory.getLogger(RestService.class);

  @Value("${api.server.base_uri}")
  private String baseApiUrl;

  private RestTemplate restTemplate = new RestTemplate();

  public Object getRequest(String url, Boolean relative){

    Map<String, String> params = new HashMap<>();
    HttpHeaders headers = new HttpHeaders();
    headers.add("User-Agent", "graphql-api");
    HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
    ResponseEntity<Map> responseEntity = null;

    try {

      if(relative) url = baseApiUrl + url;

      responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map.class, params);

    } catch (RestClientException e) {
      log.error("Error :" + e);
    }

    return responseEntity.getBody();
  }

}
