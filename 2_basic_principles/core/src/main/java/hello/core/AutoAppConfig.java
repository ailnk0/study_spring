package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixedDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import java.math.BigDecimal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =
        Configuration.class))
public class AutoAppConfig {


  @Bean
  public DiscountPolicy rateDiscountPolicy() {
    return new RateDiscountPolicy(new BigDecimal("0.1"));
  }

  @Bean
  public DiscountPolicy fixedDiscountPolicy() {
    return new FixedDiscountPolicy(100);
  }
}
