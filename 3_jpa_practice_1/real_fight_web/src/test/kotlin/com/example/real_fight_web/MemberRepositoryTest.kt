package com.example.real_fight_web

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.repository.MemberRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
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
        val member = Member(
            name = "memberA",
            address = Address("city", "street", "zipcode")
        )

        // when
        memberRepository.save(member)
        val findMember = member.id?.let { memberRepository.findOne(it) }
            ?: throw IllegalStateException("회원 정보가 없습니다.")

        // then
        Assertions.assertThat(findMember).isNotNull()
        Assertions.assertThat(findMember.id).isEqualTo(member.id)
        Assertions.assertThat(findMember.name).isEqualTo(member.name)

        // 같은 영속성인지 확인
        Assertions.assertThat(findMember).isEqualTo(member)
    }
}
