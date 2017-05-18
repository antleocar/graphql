package com.graphql.service;

import graphql.ExecutionResult;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class OperationService {

  public void checkExecutionResult(ExecutionResult executionResult, Map<String, Object> result, Logger log) {
    if (executionResult.getErrors().size() > 0) {
      result.put("errors", executionResult.getErrors());
      log.error("Errors : {}", executionResult.getErrors());
    }
  }
}
