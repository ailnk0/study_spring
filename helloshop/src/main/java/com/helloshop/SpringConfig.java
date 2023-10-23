package com.helloshop;

import com.helloshop.repository.JdbcTemplateMemberRepository;
import com.helloshop.repository.MemberRepository;
import com.helloshop.repository.MemoryMemberRepository;
import com.helloshop.service.MemberService;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

  public final static int REPO_JDBC_TEMPLATE = 1;

  public int REPO = REPO_JDBC_TEMPLATE;
  private final DataSource dataSource;

  public SpringConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    if (REPO == REPO_JDBC_TEMPLATE) {
      return new JdbcTemplateMemberRepository(dataSource);
    }
    return new MemoryMemberRepository();
  }
}
