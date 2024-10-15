package com.javalab.board.service;

import com.javalab.board.dto.PersonFormDto;
import com.javalab.board.vo.PersonVo;

import java.util.List;

public interface PersonService {

    // 일반적인 회원 저장
    void savePerson(PersonFormDto personFormDto);

    // 소셜로그인 비밀번호 및 상태 수정
    void modifyPasswordAndSocialStatus(String email, String encodedPassword);

    // 이메일로 회원 찾기
    PersonVo findPersonByEmail(String email);

    // 소셜 로그인 회원 저장 및 권한 저장
    void savePersonWithRole(PersonVo person);

    PersonVo findPersonById(String personId);

    List<PersonVo> findAllPersons();

    void updatePerson(PersonFormDto personFormDto);

    void deletePerson(String personId);
}

