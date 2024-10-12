package com.javalab.board.service;

import com.javalab.board.dto.MemberFormDto;
import com.javalab.board.vo.MemberVo;

import java.util.List;

public interface MemberService {

    // 일반적인 회원 저장
    void saveMember(MemberFormDto memberFormDto);

    // 소셜로그인 비밀번호 및 상태 수정
    void modifyPasswordAndSocialStatus(String email, String encodedPassword);

    // 이메일로 회원 찾기
    MemberVo findMemberByEmail(String email);

    // 소셜 로그인 회원 저장 및 권한 저장
    void saveMemberWithRole(MemberVo member);

    MemberVo findMemberById(String memberId);

    List<MemberVo> findAllMembers();

    void updateMember(MemberFormDto memberFormDto);

    void deleteMember(String memberId);
}

