package com.javalab.board.repository;

import com.javalab.board.vo.PersonVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonMapper {

    // 회원 저장
    void save(PersonVo person);

    // 회원의 역할 저장
    void saveRole(@Param("personId") String personId, @Param("roleId") int roleId);

    // 소셜로그인의 경우 이메일을 통해 회원 정보 조회 (로그인 용도)
    PersonVo login(@Param("email") String email);

    // 회원 아이디를 통해 회원 정보 조회
    PersonVo findById(@Param("personId") String personId);

    // 소셜 로그인 정보를 통해 회원 정보 조회
    PersonVo findPersonById(@Param("personId") String personId);

    // 모든 회원 정보 조회
    List<PersonVo> findAllPersons();

    // 회원 정보 업데이트
    void update(PersonVo person);

    // 회원 삭제
    void delete(@Param("personId") String personId);

    // 비밀번호 및 소셜 로그인 상태 수정
    void modifyPasswordAndSocialStatus(@Param("email") String email, @Param("encodedPassword") String encodedPassword);


}
