package hello.core.discount;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class RateDiscountPolicy implements DiscountPolicy {

  public static final BigDecimal MIN_DISCOUNT_RATE = new BigDecimal("0");
  public static final BigDecimal MAX_DISCOUNT_RATE = new BigDecimal("1");
  private BigDecimal rate;

  public RateDiscountPolicy(BigDecimal rate) {
    setRate(rate);
  }

  public void setRate(BigDecimal rate) {
    if (rate.compareTo(MIN_DISCOUNT_RATE) < 0 || rate.compareTo(MAX_DISCOUNT_RATE) > 0) {
      throw new IllegalArgumentException(
          "Discount rate must be between " + MIN_DISCOUNT_RATE + " and " + MAX_DISCOUNT_RATE);
    }
    this.rate = rate;
  }
}
