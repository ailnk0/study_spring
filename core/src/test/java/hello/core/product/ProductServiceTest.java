package hello.core.product;

import hello.core.AppConfig;
import hello.core.product.domain.Product;
import hello.core.product.domain.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProductServiceTest {

  ProductService service;

  @BeforeEach
  public void beforeEach() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    service = ac.getBean(ProductService.class);
  }

  @Test
  void registerProduct() {
    Product product = service.create(new Product("New Computer", 10000));
    Product findProduct = service.findById(product.getId());

    Assertions.assertThat(product).isEqualTo(findProduct);
  }
}
