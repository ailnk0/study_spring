package hello.core.bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixedDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class TreeBeanTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
      TreeBeanConfig.class);

  @Test
  void findBeanByParentType() {
    assertThrows(NoUniqueBeanDefinitionException.class, () ->
        ac.getBean(DiscountPolicy.class));
  }

  @Test
  void findBeanByNameOfType() {
    DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy",
        DiscountPolicy.class);
    assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
  }

  @Test
  void findBeanBySubType() {
    RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
    assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
  }

  @Test
  void findAllBeanByParentType() {
    Map<String, DiscountPolicy> beansOfType =
        ac.getBeansOfType(DiscountPolicy.class);
    assertThat(beansOfType.size()).isEqualTo(2);
    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + " value=" +
          beansOfType.get(key));
    }
  }

  @Test
  void findAllBeanByObjectType() {
    Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + " value=" +
          beansOfType.get(key));
    }
  }

  @Configuration
  static class TreeBeanConfig {

    @Bean
    public DiscountPolicy rateDiscountPolicy() {
      return new RateDiscountPolicy(new BigDecimal("0.1"));
    }

    @Bean
    public DiscountPolicy fixedDiscountPolicy() {
      return new FixedDiscountPolicy(1000);
    }
  }
}
