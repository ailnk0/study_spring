package hello.core.order;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountServiceImpl;
import hello.core.member.domain.Member;
import hello.core.member.domain.MemberLevel;
import hello.core.order.domain.Order;
import hello.core.order.domain.OrderService;
import hello.core.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderServiceTest {

  OrderService orderService;
  DiscountServiceImpl discountService;

  @BeforeEach
  public void beforeEach() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        AutoAppConfig.class);
    orderService = ac.getBean(OrderService.class);
    discountService = ac.getBean(DiscountServiceImpl.class);
  }

  @Test
  void orderWithDiscount() {
    Product product = new Product("New Computer", 2000);

    Member basicMember = new Member("basic@test.com", MemberLevel.BASIC);
    Order orderFromBasicMember = orderService.create(
        new Order(basicMember, product));
    discountService.discount(orderFromBasicMember, "fixedDiscountPolicy");
    Assertions.assertThat(orderFromBasicMember.getPrice()).isEqualTo(2000);
    Assertions.assertThat(orderFromBasicMember.getPayment()).isEqualTo(2000);

    Member vipMember = new Member("vip@test.com", MemberLevel.VIP);
    Order orderFromVIPMember = orderService.create(
        new Order(vipMember, product));
    discountService.discount(orderFromVIPMember, "fixedDiscountPolicy");
    Assertions.assertThat(orderFromVIPMember.getPrice()).isEqualTo(2000);
    Assertions.assertThat(orderFromVIPMember.getPayment()).isEqualTo(1900);

    Order orderFromVIPMemberWithRate = orderService.create(
        new Order(vipMember, product));
    discountService.discount(orderFromVIPMemberWithRate, "rateDiscountPolicy");
    Assertions.assertThat(orderFromVIPMemberWithRate.getPrice()).isEqualTo(2000);
    Assertions.assertThat(orderFromVIPMemberWithRate.getPayment()).isEqualTo(1800);
  }
}
