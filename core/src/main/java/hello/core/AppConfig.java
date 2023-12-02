package hello.core;

import hello.core.dataAccess.MemoryRepository;
import hello.core.dataAccess.Repository;
import hello.core.member.MemberServiceImpl;
import hello.core.member.domain.MemberService;
import hello.core.order.OrderServiceImpl;
import hello.core.order.domain.OrderService;
import hello.core.product.ProductServiceImpl;
import hello.core.product.domain.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public MemberService memberService() {
    return new MemberServiceImpl(repository());
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(repository());
  }

  @Bean
  public ProductService productService() {
    return new ProductServiceImpl(repository());
  }

  @Bean
  public Repository repository() {
    return new MemoryRepository();
  }
}
