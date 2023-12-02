package hello.core.order;

import hello.core.AppConfig;
import hello.core.discount.FixedDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.domain.Member;
import hello.core.member.domain.MemberLevel;
import hello.core.order.domain.Order;
import hello.core.order.domain.OrderService;
import hello.core.product.domain.Product;
import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderServiceTest {

  OrderService service;

  @BeforeEach
  public void beforeEach() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    service = ac.getBean(OrderService.class);
  }

  @Test
  void orderWithDiscount() {
    Product product = new Product("New Computer", 2000);

    Member basicMember = new Member("basic@test.com", MemberLevel.BASIC);
    Order orderFromBasicMember = service.create(
        new Order(basicMember, product, new FixedDiscountPolicy(100)));
    Assertions.assertThat(orderFromBasicMember.getPrice()).isEqualTo(2000);
    Assertions.assertThat(orderFromBasicMember.getPayment()).isEqualTo(2000);

    Member vipMember = new Member("vip@test.com", MemberLevel.VIP);
    Order orderFromVIPMember = service.create(
        new Order(vipMember, product, new FixedDiscountPolicy(100)));
    Assertions.assertThat(orderFromVIPMember.getPrice()).isEqualTo(2000);
    Assertions.assertThat(orderFromVIPMember.getPayment()).isEqualTo(1900);

    Order orderFromVIPMemberWithRate = service.create(
        new Order(vipMember, product, new RateDiscountPolicy(new BigDecimal("0.1"))));
    Assertions.assertThat(orderFromVIPMemberWithRate.getPrice()).isEqualTo(2000);
    Assertions.assertThat(orderFromVIPMemberWithRate.getPayment()).isEqualTo(1800);
  }
}
