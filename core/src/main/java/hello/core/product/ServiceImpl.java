package hello.core.product;

import hello.core.dataAccess.MemoryRepository;
import hello.core.dataAccess.Repository;
import hello.core.product.domain.Product;
import hello.core.product.domain.Service;

public class ServiceImpl implements Service {

  private final Repository repository = new MemoryRepository();

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
