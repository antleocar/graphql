package com.graphql.dataFetcher;


import com.graphql.rest.RestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class GenericEmbeddedDataFetcher implements DataFetcher {

  @Autowired
  private RestService restService;

  private String connectionName;
  private Boolean connection = Boolean.FALSE;

  @Override
  public Object get(DataFetchingEnvironment environment) {

    List<String> urls = new ArrayList<>();

    if (environment.getSource() != null) {
      Map map = environment.getSource();
      urls = getURLs(map);
      if (connection) {
        if (urls == null) {
          return null;
        } else if (urls.size() > 0){
          List<Object> objects = new ArrayList<>();
          getURLs(map).forEach(url -> objects.add(restService.getRequest(url, false)));
          return new SimpleListConnection(objects).get(environment);
        }

      } else {
        return restService.getRequest((String) map.get(getConnectionName()), false);

      }
    }

    return null;
  }

  private List<String> getURLs(Map source){
    return (List<String>) source.get(getConnectionName());
  }

  public String getConnectionName() {
    return connectionName;
  }

  public void setConnectionName(String connectionName) {
    this.connectionName = connectionName;
  }

  public void setConnection(Boolean connection) {
    this.connection = connection;
  }
}
