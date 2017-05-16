package com.graphql.dao;


import graphql.schema.GraphQLType;
import java.util.List;

public class User implements GraphQLType {

  private String name;
  private String userName;
  private List<User> friends;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public List<User> getFriends() {
    return friends;
  }

  public void setFriends(List<User> friends) {
    this.friends = friends;
  }

}