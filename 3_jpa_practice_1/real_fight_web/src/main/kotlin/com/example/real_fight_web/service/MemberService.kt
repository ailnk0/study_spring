package com.example.real_fight_web.service

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.repository.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun join(member: Member): Long? {
        validateDuplicateMember(member)
        memberRepository.save(member)
        return member.id
    }

    private fun validateDuplicateMember(member: Member) {
        if (memberRepository.findByName(member.name).isNotEmpty()) {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    fun findMembers(): List<Member> = memberRepository.findAll()
    fun findOne(memberId: Long): Member? = memberRepository.findOne(memberId)
}
