package hello.core.order;

import hello.core.discount.FixedDiscountPolicy;
import hello.core.member.domain.Level;
import hello.core.member.domain.Member;
import hello.core.order.domain.Order;
import hello.core.order.domain.Service;
import hello.core.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceTest {

  Service service = new ServiceImpl();

  @Test
  void orderWithDiscount() {
    Product product = new Product("New Computer", 10000);

    Member basicMember = new Member("basic@test.com", Level.BASIC);
    Order orderFromBasicMember = service.create(
        new Order(basicMember, product, new FixedDiscountPolicy(1000)));
    Assertions.assertThat(orderFromBasicMember.getPrice()).isEqualTo(10000);
    Assertions.assertThat(orderFromBasicMember.getPayment()).isEqualTo(10000);

    Member vipMember = new Member("vip@test.com", Level.VIP);
    Order orderFromVIPMember = service.create(
        new Order(vipMember, product, new FixedDiscountPolicy(1000)));
    Assertions.assertThat(orderFromVIPMember.getPrice()).isEqualTo(10000);
    Assertions.assertThat(orderFromVIPMember.getPayment()).isEqualTo(9000);
  }
}
