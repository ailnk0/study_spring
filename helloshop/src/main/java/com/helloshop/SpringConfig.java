package com.helloshop;

import com.helloshop.repository.JdbcTemplateMemberRepository;
import com.helloshop.repository.JpaMemberRepository;
import com.helloshop.repository.MemberRepository;
import com.helloshop.repository.MemoryMemberRepository;
import com.helloshop.service.MemberService;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

  public final static int REPO_JDBC_TEMPLATE = 1;
  public final static int REPO_JPA = 2;

  public int REPO = REPO_JPA;

  private final DataSource dataSource;
  private final EntityManager em;

  @Autowired
  public SpringConfig(DataSource dataSource, EntityManager em) {
    this.dataSource = dataSource;
    this.em = em;
  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    if (REPO == REPO_JDBC_TEMPLATE) {
      return new JdbcTemplateMemberRepository(dataSource);
    } else if (REPO == REPO_JPA) {
      return new JpaMemberRepository(em);
    } else {
      return new MemoryMemberRepository();
    }
  }
}
