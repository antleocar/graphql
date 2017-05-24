package com.graphql.dao.schema.types;


import graphql.relay.Relay;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeReference;
import graphql.schema.TypeResolver;
import java.util.ArrayList;

public class CarConnection {

  public GraphQLObjectType buildCarConnection() {
    Relay relay = new Relay();
    GraphQLInterfaceType interfaceType = relay.nodeInterface(new TypeResolver() {
      @Override
      public GraphQLObjectType getType(Object object) {
        Relay.ResolvedGlobalId resolvedGlobalId = relay.fromGlobalId((String)object);
        return null;
      }
    });

    GraphQLObjectType carEdgeType = relay
        .edgeType("Car",
            new GraphQLTypeReference("Car"),
            interfaceType,
            new ArrayList<>());

    return relay.connectionType("Car", carEdgeType, new ArrayList<>());
  }

}
