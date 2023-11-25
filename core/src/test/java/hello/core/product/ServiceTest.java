package hello.core.product;

import hello.core.AppConfig;
import hello.core.product.domain.Product;
import hello.core.product.domain.Service;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServiceTest {

  Service service;

  @BeforeEach
  public void beforeEach() {
    AppConfig config = new AppConfig();
    service = config.productService();
  }

  @Test
  void registerProduct() {
    Product product = service.create(new Product("New Computer", 10000));
    Product findProduct = service.findById(product.getId());

    Assertions.assertThat(product).isEqualTo(findProduct);
  }
}
