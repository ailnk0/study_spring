package hello.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public hello.core.member.domain.Service memberService() {
    return new hello.core.member.ServiceImpl(repository());
  }

  @Bean
  public hello.core.order.domain.Service orderService() {
    return new hello.core.order.ServiceImpl(repository());
  }

  @Bean
  public hello.core.product.domain.Service productService() {
    return new hello.core.product.ServiceImpl(repository());
  }

  @Bean
  public hello.core.dataAccess.Repository repository() {
    return new hello.core.dataAccess.MemoryRepository();
  }
}
