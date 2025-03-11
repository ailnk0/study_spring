package com.example.real_fight_web.api

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.service.*
import jakarta.validation.*
import org.springframework.web.bind.annotation.*

@RestController
class MemberApiController(
    private val memberService: MemberService
) {
    data class SaveMemberRequest(
        val name: String
    ) {
        fun toEntity(): Member {
            return Member(name = name)
        }
    }

    data class SaveMemberResponse(
        val id: Long
    )

    @PostMapping("/api/v1/member")
    fun saveMemberV1(@RequestBody @Valid member: Member): SaveMemberResponse {
        val id = memberService.join(member) ?: throw IllegalStateException("회원 가입에 실패했습니다.")
        return SaveMemberResponse(id)
    }

    @PostMapping("/api/v2/member")
    fun saveMemberV2(@RequestBody @Valid request: SaveMemberRequest): SaveMemberResponse {
        val id = memberService.join(request.toEntity()) ?: throw IllegalStateException("회원 가입에 실패했습니다.")
        return SaveMemberResponse(id)
    }


    data class UpdateMemberRequest(
        val name: String
    )

    data class UpdateMemberResponse(
        val id: Long,
        val name: String
    )

    @PutMapping("/api/v2/member/{id}")
    fun updateMemberV2(
        @PathVariable id: Long,
        @RequestBody @Valid request: UpdateMemberRequest
    ): UpdateMemberResponse {
        memberService.update(id, request.name)
        val findMember = memberService.findOne(id) ?: throw IllegalStateException("회원 정보가 없습니다.")
        return UpdateMemberResponse(id, findMember.name)
    }


    data class MemberDto(
        val name: String
    )

    @GetMapping("/api/v1/member")
    fun findMembersV1(): List<Member> {
        return memberService.findMembers()
    }

    @GetMapping("/api/v2/member")
    fun findMembersV2(): List<MemberDto> {
        val members = memberService.findMembers()
        return members.map { MemberDto(it.name) }
    }
}
