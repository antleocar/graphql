package com.graphql.dao.schema;
import static graphql.schema.GraphQLSchema.newSchema;

import com.graphql.dao.schema.types.CarConnection;
import com.graphql.dao.schema.types.CarType;
import com.graphql.dao.schema.types.UserConnection;
import com.graphql.dao.schema.types.UserType;
import com.graphql.dataFetcher.ConnectionDataFetcher;
import com.graphql.dataFetcher.IdDataFetcher;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectSchema {

  @Autowired
  private UserType userType;

  @Autowired
  private CarType carType;

  @Autowired
  private IdDataFetcher userIdDataFetcher;

  @Autowired
  private IdDataFetcher carIdDataFetcher;

  @Autowired
  private ConnectionDataFetcher userConnectionDataFetcher;

  @Autowired
  private ConnectionDataFetcher carConnectionDataFetcher;


  public GraphQLSchema build() {
    return newSchema().query(buildQuery()).build(buildDictionary());
  }

  private Set<GraphQLType> buildDictionary() {
    Set<GraphQLType> types = new HashSet<>();

    types.add(carType.buildCarType());
    types.add(userType.buildUserType());

    types.add(new UserConnection().buildUserConnection());
    types.add(new CarConnection().buildCarConnection());

    return types;
  }

  private GraphQLObjectType buildQuery() {
    return null;
  }

}
