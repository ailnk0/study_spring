package com.example.real_fight_web

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    lateinit var memberRepository: MemberRepository

    @Transactional
    @Test
    // @Transactional @Test가 끝난후 롤백하는데 롤백을 하지 않게 하려면 @Rollback(false)를 붙여준다.
    // @Rollback(false)
    fun testMember() {
        // given
        val member = Member()
        member.username = "memberA"

        // when
        val savedId = memberRepository.save(member)
        val findMember = memberRepository.find(savedId)

        // then
        Assertions.assertThat(findMember.id).isEqualTo(member.id)
        Assertions.assertThat(findMember.username).isEqualTo(member.username)

        // 같은 영속성인지 확인
        Assertions.assertThat(findMember).isEqualTo(member)
    }
}