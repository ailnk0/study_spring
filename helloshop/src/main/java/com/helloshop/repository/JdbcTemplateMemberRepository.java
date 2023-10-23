package com.helloshop.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcTemplateMemberRepository implements MemberRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateMemberRepository(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Optional<Member> save(Member member) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("email", member.getEmail());
    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
    member.setId(key.longValue());
    return Optional.of(member);
  }

  @Override
  public Optional<Member> findById(Long id) {
    List<Member> result = jdbcTemplate
        .query("select * from member where id = ?", memberRowMapper(), id);
    return result.stream().findAny();
  }

  @Override
  public Optional<Member> findByEmail(String email) {
    List<Member> result = jdbcTemplate.query("select * from member where email = ?",
        memberRowMapper(), email);
    return result.stream().findAny();
  }

  @Override
  public List<Member> findAll() {
    return jdbcTemplate
        .query("select * from member", memberRowMapper());
  }

  @Override
  public void clear() {
  }

  private RowMapper<Member> memberRowMapper() {
    return (rs, rowNum) ->
        new Member(
            rs.getLong("id"),
            rs.getString("email"));
  }
}
