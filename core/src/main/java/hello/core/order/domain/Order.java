package hello.core.order.domain;

import hello.core.dataAccess.Item;
import hello.core.discount.DiscountPolicy;
import hello.core.member.domain.Member;
import hello.core.product.domain.Product;

public class Order extends Item {

  Member buyer = null;
  Product product = null;
  DiscountPolicy discountPolicy = null;
  int price = 0;
  int payment = 0;

  public Order(Member buyer, Product product, DiscountPolicy discountPolicy) {
    this.buyer = buyer;
    this.product = product;
    this.discountPolicy = discountPolicy;
  }

  public Member getBuyer() {
    return buyer;
  }

  public void setBuyer(Member buyer) {
    this.buyer = buyer;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public DiscountPolicy getDiscountPolicy() {
    return discountPolicy;
  }

  public void setDiscountPolicy(DiscountPolicy discountPolicy) {
    this.discountPolicy = discountPolicy;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getPayment() {
    return payment;
  }

  public void setPayment(int payment) {
    this.payment = payment;
  }
}
