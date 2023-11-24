package hello.core.product.domain;

public interface Service {

  Product create(Product product);

  Product update(Product product);

  void deleteById(String id);

  Product findById(String id);
}
