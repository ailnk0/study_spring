package hello.core.product.domain;

import hello.core.dataAccess.Item;

public class Product extends Item {

  String name = "";
  int price = 0;

  public Product(String name, int price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return this.name;
  }

  public int getPrice() {
    return this.price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
