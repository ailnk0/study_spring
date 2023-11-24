package hello.core.order.domain;

public interface Service {

  Order create(Order order);

  Order update(Order order);

  void deleteById(String id);

  Order findById(String id);
}
