package hello.core.discount;

import hello.core.order.domain.Order;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountServiceImpl {

  final Map<String, DiscountPolicy> discountPolicyMap;
  final List<DiscountPolicy> discountPolicyList;

  public void discount(Order order, String discountPolicyName) {
    DiscountPolicy discountPolicy;
    try {
      discountPolicy = discountPolicyMap.get(discountPolicyName);
    } catch (Exception e) {
      throw new IllegalArgumentException("No such discount policy: " + discountPolicyName);
    }

    switch (order.getBuyer().getLevel()) {
      case BASIC -> order.setPayment(order.getProduct().getPrice());
      case VIP -> {
        if (discountPolicy instanceof FixedDiscountPolicy policy) {
          order.setPayment(order.getProduct().getPrice() - policy.amount());
        } else if (discountPolicy instanceof RateDiscountPolicy policy) {
          BigDecimal newPrice = new BigDecimal(order.getProduct().getPrice());
          newPrice = newPrice.multiply(new BigDecimal("1").subtract(policy.getRate()));
          order.setPayment(newPrice.toBigInteger().intValue());
        } else {
          order.setPayment(order.getProduct().getPrice());
        }
      }
      default ->
          throw new IllegalStateException("Unexpected value: " + order.getBuyer().getLevel());
    }
  }
}
