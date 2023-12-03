package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThirdPartyLib {

  String name;

  public ThirdPartyLib() {
    System.out.println(name + " : constructor");
  }

  @PostConstruct
  public void init() {
    System.out.println(name + " : init");
  }

  @PreDestroy
  public void close() {
    System.out.println(name + " : close");
  }
}
