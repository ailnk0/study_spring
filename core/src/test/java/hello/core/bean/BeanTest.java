package hello.core.bean;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  void findAllBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("name = " + beanDefinitionName + " object = " + bean);
    }
  }

  @Test
  void findApplicationBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition =
          ac.getBeanDefinition(beanDefinitionName);

      if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        Object bean = ac.getBean(beanDefinitionName);
        System.out.println("name=" + beanDefinitionName + " object=" +
            bean);
      }
    }
  }

  @Test
  void findBeanByName() {
    hello.core.member.domain.Service memberService = ac.getBean("memberService",
        hello.core.member.domain.Service.class);
    assertThat(memberService).isInstanceOf(hello.core.member.domain.Service.class);
  }

  @Test
  void findBeanByConcrete() {
    hello.core.member.domain.Service memberService = ac.getBean(
        hello.core.member.ServiceImpl.class);
    assertThat(memberService).isInstanceOf(hello.core.member.domain.Service.class);
  }

  @Test
  void findBeanByWrongName() {
    Assertions.assertThrows(NoSuchBeanDefinitionException.class,
        () -> ac.getBean("WrongName", hello.core.member.domain.Service.class));
  }

  @Test
  void findBeanByType() {
    hello.core.member.domain.Service memberService = ac.getBean(
        hello.core.member.domain.Service.class);
    assertThat(memberService).isInstanceOf(hello.core.member.domain.Service.class);
  }
}
