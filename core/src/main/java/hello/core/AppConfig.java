package hello.core;

public class AppConfig {

  public hello.core.member.domain.Service memberService() {
    return new hello.core.member.ServiceImpl(repository());
  }

  public hello.core.order.domain.Service orderService() {
    return new hello.core.order.ServiceImpl(repository());
  }

  public hello.core.product.domain.Service productService() {
    return new hello.core.product.ServiceImpl(repository());
  }

  public hello.core.dataAccess.Repository repository() {
    return new hello.core.dataAccess.MemoryRepository();
  }
}
