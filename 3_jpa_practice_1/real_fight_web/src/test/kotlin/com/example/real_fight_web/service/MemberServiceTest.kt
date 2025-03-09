package com.example.real_fight_web.service

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.repository.*
import jakarta.persistence.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.test.context.junit.jupiter.*
import org.springframework.transaction.annotation.*


@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var em: EntityManager

    @Test
    // 테스트에서 DB에 실제 저장된 것을 확인해보고 싶다면 롤백을 해제한다.
    // @Rollback(false)
    fun join() {
        val member = Member(
            name = "memberA",
            address = Address("city", "street", "zipcode")
        )

        memberService.join(member)

        // 테스트에서는 실제로, DB에 flush하지 않는다.
        // 강제로 flush 수행해서 insert 확인 방법.
        // em.flush()

        val findMember = member.id?.let { memberRepository.findOne(it) }
            ?: throw IllegalStateException("회원 정보가 없습니다.")

        assertEquals(member, findMember)
    }

    @Test
    fun validateDuplicateMember() {
        val member1 = Member(
            name = "memberA",
            address = Address("city", "street", "zipcode")
        )

        val member2 = Member(
            name = "memberA",
            address = Address("city", "street", "zipcode")
        )

        memberService.join(member1)
        assertThrows<IllegalStateException> {
            memberService.join(member2)
        }
    }

    @Test
    fun findMembers() {
        val members = listOf(
            Member(
                name = "memberA",
                address = Address("city", "street", "zipcode")
            ),
            Member(
                name = "memberB",
                address = Address("city", "street", "zipcode")
            )
        )

        members.forEach { memberService.join(it) }

        assertEquals(2, memberService.findMembers().size)
    }

    @Test
    fun findOne() {
        val member = Member(
            name = "memberA",
            address = Address("city", "street", "zipcode")
        )

        memberService.join(member)

        val findMember = member.id?.let { memberRepository.findOne(it) }
            ?: throw IllegalStateException("회원 정보가 없습니다.")

        assertEquals(member, findMember)
    }
}
