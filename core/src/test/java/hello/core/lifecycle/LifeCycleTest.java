package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class LifeCycleTest {

  @Configuration
  static class LifeCycleConfig1 {

    @Bean
    public ThirdPartyLib thirdPartyLib1() {
      ThirdPartyLib lib = new ThirdPartyLib();
      lib.setName("lib1");
      return lib;
    }
  }

  @Test
  public void lifeCycleTest1() {
    ConfigurableApplicationContext ac = new
        AnnotationConfigApplicationContext(LifeCycleConfig1.class);
    ThirdPartyLib lib = ac.getBean(ThirdPartyLib.class);
    ac.close();
  }

  @Configuration
  static class LifeCycleConfig2 {

    @Bean(initMethod = "init", destroyMethod = "close")
    public ThirdPartyLib thirdPartyLib2() {
      ThirdPartyLib lib = new ThirdPartyLib();
      lib.setName("lib2");
      return lib;
    }
  }

  @Test
  public void lifeCycleTest2() {
    ConfigurableApplicationContext ac = new
        AnnotationConfigApplicationContext(LifeCycleConfig2.class);
    ThirdPartyLib lib = ac.getBean(ThirdPartyLib.class);
    ac.close();
  }
}
