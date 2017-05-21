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
public class CarType {

  @Autowired
  private GenericEmbeddedDataFetcher genericEmbeddedDataFetcher;


  public GraphQLObjectType buildCarType() {

    genericEmbeddedDataFetcher.setConnection(Boolean.TRUE);
    genericEmbeddedDataFetcher.setConnectionName("users");

    Relay relay = new Relay();

    return newObject()
        .name("Car")
        .field(newFieldDefinition()
            .name("_id")
            .description("Id object")
            .type(Scalars.GraphQLString)
            .build())
        .field(newFieldDefinition()
            .name("model")
            .description("Model of a car")
            .type(Scalars.GraphQLString)
            .build())
        .field(newFieldDefinition()
            .name("price")
            .description("Price of a car")
            .type(Scalars.GraphQLFloat)
            .build())
        .field(newFieldDefinition()
            .name("userConnection")
            .description("Relations with users")
            .type(new GraphQLTypeReference("UserConnection"))
            .argument(relay.getConnectionFieldArguments())
            .dataFetcher(genericEmbeddedDataFetcher)
            .build())
        .build();
  }

}
