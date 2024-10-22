package com.javalab.board.service;

import com.javalab.board.vo.MemberVo;
import com.javalab.board.vo.UserRolesVo;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    void registerMember(MemberVo memberVo, UserRolesVo userRolesVo, MultipartFile file) throws Exception;
    void registerMember(MemberVo memberVo, UserRolesVo userRolesVo) throws Exception; // 새로운 메소드
    MemberVo findMemberByEmail(String email);


    // 필요한 추가 메서드 선언
//    // 소셜로그인 비밀번호 및 상태 수정
//    void modifyPasswordAndSocialStatus(String email, String encodedPassword);
//
//    // 소셜 로그인 회원 저장 및 권한 저장
//    void saveMemberWithRole(MemberVo member);
//
//    MemberVo findMemberById(String memberId);
//
//    List<MemberVo> findAllMembers();
//
//    void updateMember(MemberFormDto memberFormDto);
//
//    void deleteMember(String memberId);

}

