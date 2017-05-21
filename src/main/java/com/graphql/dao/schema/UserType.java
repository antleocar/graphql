package com.graphql.dao.schema;

import com.graphql.dataFetcher.GenericEmbeddedDataFetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.Scalars;
import graphql.relay.Relay;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeReference;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

@Component
public class UserType {

  @Autowired
  private GenericEmbeddedDataFetcher genericEmbeddedDataFetcher;

  public GraphQLObjectType buildUserType() {

    genericEmbeddedDataFetcher.setConnection(Boolean.TRUE);
    genericEmbeddedDataFetcher.setConnectionName("cars");

    Relay relay = new Relay();

    return newObject()
        .name("User")
        .field(newFieldDefinition()
            .name("_id")
            .description("Id user")
            .type(Scalars.GraphQLString)
            .build())
        .field(newFieldDefinition()
            .name("name")
            .description("Name of the user")
            .type(Scalars.GraphQLString)
            .build())
        .field(newFieldDefinition()
            .name("UserName")
            .description("Username of the user")
            .type(Scalars.GraphQLString)
            .build())
        .field(newFieldDefinition()
            .name("carConnection")
            .description("Relations with cars")
            .type(new GraphQLTypeReference("carConnection"))
            .argument(relay.getConnectionFieldArguments())
            .dataFetcher(genericEmbeddedDataFetcher)
            .build())
        .field(newFieldDefinition()
            .name("userConnection")
            .description("Relations with friends")
            .type(new GraphQLTypeReference("UserConnection"))
            .argument(relay.getConnectionFieldArguments())
            .dataFetcher(genericEmbeddedDataFetcher)
            .build())
        .build();

  }

}
