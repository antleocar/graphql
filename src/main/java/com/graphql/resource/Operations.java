package com.graphql.resource;

import com.graphql.service.OperationService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/graphql")
public class Operations {

  private static Logger log = LoggerFactory.getLogger(Operations.class);

  GraphQL graphQL = new GraphQL();

  @Autowired
  private OperationService operationService;

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Object insertData(@RequestBody Map map) {
    String query = map.get("query").toString();
    Map<String, Object> vars = (Map<String, Object>) map.get("vars");

    ExecutionResult executionResult = graphQL.execute(query, (Object) null, vars);
    Map<String, Object> result = new LinkedHashMap<>();
    operationService.checkExecutionResult(executionResult, result, log);

    result.put("data", executionResult.getData());
    return result;
  }

}
