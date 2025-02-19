package hello.core.bean;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.AppConfig;
import hello.core.member.MemberServiceImpl;
import hello.core.member.domain.MemberService;
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
    MemberService memberService = ac.getBean("memberService",
        MemberService.class);
    assertThat(memberService).isInstanceOf(MemberService.class);
  }

  @Test
  void findBeanByConcrete() {
    MemberService memberService = ac.getBean(
        MemberServiceImpl.class);
    assertThat(memberService).isInstanceOf(MemberService.class);
  }

  @Test
  void findBeanByWrongName() {
    Assertions.assertThrows(NoSuchBeanDefinitionException.class,
        () -> ac.getBean("WrongName", MemberService.class));
  }

  @Test
  void findBeanByType() {
    MemberService memberService = ac.getBean(
        MemberService.class);
    assertThat(memberService).isInstanceOf(MemberService.class);
  }
}
