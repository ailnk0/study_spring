package hello.core.order;

import hello.core.dataAccess.Repository;
import hello.core.order.domain.Order;
import hello.core.order.domain.OrderService;
import hello.core.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final Repository repository;

  @Override
  public Order create(Order order) {
    Product product = order.getProduct();
    order.setPrice(product.getPrice());

    return (Order) repository.save(order);
  }

  @Override
  public Order update(Order order) {
    return (Order) repository.save(order);
  }

  @Override
  public void deleteById(String id) {
    repository.deleteById(id);
  }

  @Override
  public Order findById(String id) {
    return (Order) repository.findById(id);
  }
}
