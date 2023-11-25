package hello.core.order;

import hello.core.dataAccess.Repository;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixedDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.domain.Member;
import hello.core.order.domain.Order;
import hello.core.order.domain.Service;
import hello.core.product.domain.Product;
import java.math.BigDecimal;

public class ServiceImpl implements Service {

  private final Repository repository;

  public ServiceImpl(Repository repository) {
    this.repository = repository;
  }

  @Override
  public Order create(Order order) {

    Member buyer = order.getBuyer();
    Product product = order.getProduct();
    DiscountPolicy discountPolicy = order.getDiscountPolicy();

    order.setPrice(product.getPrice());

    switch (buyer.getLevel()) {
      case BASIC -> order.setPayment(product.getPrice());
      case VIP -> {
        if (discountPolicy instanceof FixedDiscountPolicy policy) {
          order.setPayment(product.getPrice() - policy.amount());
        } else if (discountPolicy instanceof RateDiscountPolicy policy) {
          BigDecimal newPrice = new BigDecimal(product.getPrice());
          newPrice = newPrice.multiply(new BigDecimal("1").subtract(policy.getRate()));
          order.setPayment(newPrice.toBigInteger().intValue());
        } else {
          order.setPayment(product.getPrice());
        }
      }
      default -> throw new IllegalStateException("Unexpected value: " + buyer.getLevel());
    }

    return (Order) repository.save(order);
  }

  @Override
  public Order update(Order order) {
    return (Order) repository.save(order);
  }

  @Override
  public void deleteById(String id) {
    repository.deleteById(id);
  }

  @Override
  public Order findById(String id) {
    return (Order) repository.findById(id);
  }
}
