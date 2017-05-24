package com.graphql.dao.schema.types;

import com.graphql.dataFetcher.EmbeddedDataFetcher;

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
  private EmbeddedDataFetcher embeddedDataFetcher;

  public GraphQLObjectType buildUserType() {

    embeddedDataFetcher.setConnection(Boolean.TRUE);
    embeddedDataFetcher.setConnectionName("cars");

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
            .dataFetcher(embeddedDataFetcher)
            .build())
        .field(newFieldDefinition()
            .name("userConnection")
            .description("Relations with friends")
            .type(new GraphQLTypeReference("UserConnection"))
            .argument(relay.getConnectionFieldArguments())
            .dataFetcher(embeddedDataFetcher)
            .build())
        .build();

  }

}
