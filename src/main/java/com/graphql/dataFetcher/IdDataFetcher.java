package com.graphql.dataFetcher;


import com.graphql.rest.RestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class IdDataFetcher implements DataFetcher {

  @Autowired
  private RestService restService;

  private String url;

  @Override
  public Object get(DataFetchingEnvironment environment) {

    if (environment.getArgument("id") != null) {
      return restService.getRequest(getUrl()
                                        .concat(environment.getArgument("id").toString())
                                        .concat("/"), true);
    }
    return null;
  }

  public String getUrl() {
    return url;
  }
}
