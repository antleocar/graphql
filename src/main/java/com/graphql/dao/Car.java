package com.graphql.dao;

public class Car {

  private String model;
  private Double price;

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Car(String model, Double price) {
    this.model = model;
    this.price = price;
  }
}
