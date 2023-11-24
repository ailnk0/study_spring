package hello.core.product;

import hello.core.product.domain.Product;
import hello.core.product.domain.Service;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceTest {

  Service service = new ServiceImpl();

  @Test
  void registerProduct() {
    Product product = service.create(new Product("New Computer", 10000));
    Product findProduct = service.findById(product.getId());

    Assertions.assertThat(product).isEqualTo(findProduct);
  }
}
