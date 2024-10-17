package com.javalab.board.service;

import com.javalab.board.dto.PersonFormDto;
import com.javalab.board.repository.PersonMapper;
import com.javalab.board.vo.PersonVo;
import com.javalab.board.vo.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonMapper personMapper;
    private final ModelMapper modelMapper;

    /**
     * 회원 저장시 사용
     * - 회원 정보 저장
     * - 회원 역할 저장, 회원 역할은 기본적으로 'ROLE_USER'로 저장
     * @param personFormDto
     */
    @Override
    public void savePerson(PersonFormDto personFormDto) {
        // modelMapper를 이용해서 DTO를 VO로 쉽게 변환
        PersonVo personVo = modelMapper.map(personFormDto, PersonVo.class);
        // 회원 저장
        personMapper.save(personVo);
        // 회원 역할 저장
        personMapper.saveRole(personVo.getPersonId(), 1); // 권한은 1, ROLE_USER 하드코딩
    }


    /**
     * 소셜 로그인 회원의 비밀번호 및 소셜 로그인 상태 수정
     * @param email
     * @param encodedPassword
     */
    @Override
    public void modifyPasswordAndSocialStatus(String email,
                                              String encodedPassword) {

        personMapper.modifyPasswordAndSocialStatus(email, encodedPassword);
    }

    /**
     * 소셜로그인 회원 정보 저장시 사용
     * - 회원 정보 저장
     * - 회원 역할 저장
     * @param person
     */
    @Override
    public void savePersonWithRole(PersonVo person) {
        log.info("savePersonWithRole....{}", person);

        // 사용자 저장
        personMapper.save(person);

        // 회원의 역할 저장
        if (!person.getRoles().isEmpty()) {
            Role role = person.getRoles().get(0);  // 첫 번째 역할만 저장
            personMapper.saveRole(person.getPersonId(), role.getRoleId());
        }
    }

    /**
     * 소셜로그인의 경우  이메일을 통해 회원 정보 조회
     * @param email
     * @return
     */
    @Override
    public PersonVo findPersonByEmail(String email) {
        return personMapper.login(email);
    }

    @Override
    public PersonVo findPersonById(String personId) {
        return personMapper.findPersonById(personId);
    }

    @Override
    public List<PersonVo> findAllPersons() {
        return personMapper.findAllPersons();
    }

    @Override
    public void updatePerson(PersonFormDto personFormDto) {
        // DTO를 VO로 변환
        PersonVo personVo = modelMapper.map(personFormDto, PersonVo.class);
        personMapper.update(personVo);
    }

    @Override
    public void deletePerson(String personId) {
        personMapper.delete(personId);
    }
}
