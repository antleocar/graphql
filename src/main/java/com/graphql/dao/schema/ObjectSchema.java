package com.graphql.dao.schema;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphQLSchema.newSchema;

import com.graphql.dao.schema.types.CarConnection;
import com.graphql.dao.schema.types.CarType;
import com.graphql.dao.schema.types.UserConnection;
import com.graphql.dao.schema.types.UserType;
import com.graphql.dataFetcher.ConnectionDataFetcher;
import com.graphql.dataFetcher.IdDataFetcher;
import graphql.Scalars;
import graphql.relay.Relay;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;
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

    Relay relay = new Relay();

    carIdDataFetcher.setUrl("/cars/");
    userIdDataFetcher.setUrl("/users/");

    carConnectionDataFetcher.setUrl("/cars/");
    userConnectionDataFetcher.setUrl("/users/");

    return newObject()
        .name("Query")
        .description("Master Query")
        .field(newFieldDefinition()
            .name("user")
            .argument(newArgument()
                .name("id")
                .type(new GraphQLNonNull(Scalars.GraphQLInt)))
            .type(new GraphQLTypeReference("User"))
            .dataFetcher(userIdDataFetcher))
        .field(newFieldDefinition()
            .name("car")
            .argument(newArgument()
                .name("id")
                .type(new GraphQLNonNull(Scalars.GraphQLInt)))
            .type(new GraphQLTypeReference("Car"))
            .dataFetcher(carIdDataFetcher))
        .field(newFieldDefinition()
            .name("All users")
            .type(new GraphQLTypeReference("UserConnection"))
            .argument(relay.getConnectionFieldArguments())
            .dataFetcher(userConnectionDataFetcher))
        .field(newFieldDefinition()
            .name("All cars")
            .type(new GraphQLTypeReference("CarConnection"))
            .argument(relay.getConnectionFieldArguments())
            .dataFetcher(carConnectionDataFetcher))
        .build();
  }

}
