package com.graphql.dataFetcher;


import com.graphql.rest.RestService;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionDataFetcher implements DataFetcher {

  @Autowired
  private RestService restService;

  private String url;

  @Override
  public Object get(DataFetchingEnvironment environment) {
    List<Object> objects = new ArrayList<>();
    Map map = (Map) restService.getRequest(getUrl(), true);
    while(map != null){
      List results = (List) map.get("results");
      String next = (String) map.get("next");

      results.forEach(object -> objects.add(objects));
      map = null;
      if(next != null) {
        map = (Map) restService.getRequest(next, false);
      }
    }

    if(objects != null){
      return new SimpleListConnection(objects).get(environment);
    }
    return null;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) { this.url = url; }
}
