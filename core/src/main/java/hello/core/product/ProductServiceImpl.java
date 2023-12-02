package hello.core.product;

import hello.core.dataAccess.Repository;
import hello.core.product.domain.Product;
import hello.core.product.domain.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final Repository repository;

  @Override
  public Product create(Product product) {
    return (Product) repository.save(product);
  }

  @Override
  public Product update(Product product) {
    return (Product) repository.save(product);
  }

  @Override
  public void deleteById(String id) {
    repository.deleteById(id);
  }

  @Override
  public Product findById(String id) {
    return (Product) repository.findById(id);
  }
}
