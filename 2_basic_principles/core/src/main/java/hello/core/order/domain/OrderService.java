package hello.core.order.domain;

public interface OrderService {

  Order create(Order order);

  Order update(Order order);

  void deleteById(String id);

  Order findById(String id);
}
