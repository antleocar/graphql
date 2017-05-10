package com.graphql;


import static graphql.Scalars.GraphQLBoolean;
import static graphql.Scalars.GraphQLString;

import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import java.util.Map;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

public class ObjectCreation {

  GraphQLObjectType user = newObject()
      .name("User")
      .description("A user")
      .field(newFieldDefinition()
          .name("name")
          .staticValue("John Doe")
          .description("The name of the user")
          .type(GraphQLString))
      .field(newFieldDefinition()
          .name("principalUser")
          .description("Principal User")
          .type(GraphQLBoolean))
      .build();

  GraphQLSchema graphQLSchema = GraphQLSchema.newSchema().query(user).build();

  GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

  Map<String, Object> map = graphQL.execute("{name}").getData();

}
