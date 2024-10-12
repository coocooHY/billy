package com.javalab.board.repository;

import com.javalab.board.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 회원 저장
    void save(MemberVo member);

    // 회원의 역할 저장
    void saveRole(@Param("memberId") String memberId, @Param("roleId") int roleId);

    // 소셜로그인의 경우 이메일을 통해 회원 정보 조회 (로그인 용도)
    MemberVo login(@Param("email") String email);

    // 회원 아이디를 통해 회원 정보 조회
    MemberVo findById(@Param("memberId") String memberId);

    // 소셜 로그인 정보를 통해 회원 정보 조회
    MemberVo findMemberById(@Param("memberId") String memberId);

    // 모든 회원 정보 조회
    List<MemberVo> findAllMembers();

    // 회원 정보 업데이트
    void update(MemberVo member);

    // 회원 삭제
    void delete(@Param("memberId") String memberId);

    // 비밀번호 및 소셜 로그인 상태 수정
    void modifyPasswordAndSocialStatus(@Param("email") String email, @Param("encodedPassword") String encodedPassword);


}
