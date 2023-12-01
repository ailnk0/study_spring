package hello.core.bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.core.dataAccess.MemoryRepository;
import hello.core.dataAccess.Repository;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class DuplicatedBeanTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
      DuplicatedBeanConfig.class);

  @Test
  void findBeanByTypeDuplicate() {
    assertThrows(NoUniqueBeanDefinitionException.class, () ->
        ac.getBean(Repository.class));
  }

  @Test
  void findBeanByName() {
    Repository repo = ac.getBean("repository1",
        Repository.class);
    assertThat(repo).isInstanceOf(Repository.class);
  }

  @Test
  void findAllBeanByType() {
    Map<String, Repository> beansOfType =
        ac.getBeansOfType(Repository.class);
    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + " value = " +
          beansOfType.get(key));
    }
    System.out.println("beansOfType = " + beansOfType);
    assertThat(beansOfType.size()).isEqualTo(2);
  }

  @Configuration
  static class DuplicatedBeanConfig {

    @Bean
    public Repository repository1() {
      return new MemoryRepository();
    }

    @Bean
    public Repository repository2() {
      return new MemoryRepository();
    }
  }
}
