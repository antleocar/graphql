package com.graphql;


import static graphql.Scalars.GraphQLFloat;
import static graphql.Scalars.GraphQLString;

import graphql.GraphQL;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.TypeResolver;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInterfaceType.newInterface;
import static graphql.schema.GraphQLObjectType.newObject;

@Service
public class ObjectSchema {

  public static GraphQLInterfaceType qlInterfaceType = newInterface()
      .name("GraphQLType")
      .field(newFieldDefinition()
        .name("name")
        .type(GraphQLString))
      .build();

  public static GraphQLObjectType UserType = newObject()
      .name("User")
      .field(newFieldDefinition()
        .name("name")
        .type(GraphQLString))
      .field(newFieldDefinition()
        .name("userName")
        .type(GraphQLString))
      .field(newFieldDefinition()
        .name("friends")
        .type(new GraphQLList(qlInterfaceType)))
      .build();

  public static GraphQLObjectType CarType = newObject()
      .name("Car")
      .field(newFieldDefinition()
        .name("model")
        .type(GraphQLString))
      .field(newFieldDefinition()
        .name("price")
        .type(GraphQLFloat)).build();


  public void run() {

    GraphQLSchema graphQLSchema = GraphQLSchema.newSchema().query(CarType).build();

    GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

    Map<String, Object> map = graphQL.execute("{model}").getData();

    System.out.println(map);

  }

  @PostConstruct
  void posInit() {
    this.run();

  }

}
