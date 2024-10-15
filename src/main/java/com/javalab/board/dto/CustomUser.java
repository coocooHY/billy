package com.javalab.board.dto;

import com.javalab.board.vo.PersonVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
public class CustomUser extends User implements OAuth2User, Serializable {

    private static final long serialVersionUID = 1L;

    private PersonVo personVo;  // 일반시큐리티 로그인
    private Map<String, Object> attributes; // 소셜 로그인 정보

    public CustomUser(String username, String password,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        log.info("PersonDto 생성자 호출 -1 ");
    }

    // 일반 시큐리티 사용
    public CustomUser(PersonVo personVo) {
        super(personVo.getPersonId(),
                personVo.getPassword(),
                personVo.getRoles().stream()
                        .map(g -> new SimpleGrantedAuthority(g.getRoleName()))
                        .collect(Collectors.toList()));

        log.info("PersonDto 생성자 호출 - 2");

        this.personVo = personVo;
    }

    // 소셜 로그인 사용자용 생성자, 파라미터로 attributes 추가됨.
    public CustomUser(PersonVo personVo,
                      Map<String, Object> attributes) {

        super(personVo.getPersonId(),
                personVo.getPassword(),
                personVo.getRoles().stream()
                        .map(g -> new SimpleGrantedAuthority(g.getRoleName()))
                        .collect(Collectors.toList()));

        log.info("CustomUser 생성자 호출 - 소셜 로그인");

        this.personVo = personVo;
        this.attributes = attributes;
    }

    @Override
    public String getUsername() {
        return this.personVo.getPersonId(); // 여기서 personId를 반환
    }

    public String getPassword() {
        return this.personVo.getPassword();
    }

    public String getName() {
        return this.personVo.getName();
    }

    public String getEmail() {
        return this.personVo.getEmail();
    }

//    public boolean isSocial() {
//        return this.personVo.getSocial() == 1;
//    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

}
