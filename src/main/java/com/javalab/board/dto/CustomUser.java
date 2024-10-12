package com.javalab.board.dto;

import com.javalab.board.vo.MemberVo;
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

    private MemberVo memberVo;  // 일반시큐리티 로그인
    private Map<String, Object> attributes; // 소셜 로그인 정보

    public CustomUser(String username, String password,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        log.info("MemberDto 생성자 호출 -1 ");
    }

    // 일반 시큐리티 사용
    public CustomUser(MemberVo memberVo) {
        super(memberVo.getMemberId(),
                memberVo.getPassword(),
                memberVo.getRoles().stream()
                        .map(g -> new SimpleGrantedAuthority(g.getRoleName()))
                        .collect(Collectors.toList()));

        log.info("MemberDto 생성자 호출 - 2");

        this.memberVo = memberVo;
    }

    // 소셜 로그인 사용자용 생성자, 파라미터로 attributes 추가됨.
    public CustomUser(MemberVo memberVo,
                      Map<String, Object> attributes) {

        super(memberVo.getMemberId(),
                memberVo.getPassword(),
                memberVo.getRoles().stream()
                        .map(g -> new SimpleGrantedAuthority(g.getRoleName()))
                        .collect(Collectors.toList()));

        log.info("CustomUser 생성자 호출 - 소셜 로그인");

        this.memberVo = memberVo;
        this.attributes = attributes;
    }

    @Override
    public String getUsername() {
        return this.memberVo.getMemberId(); // 여기서 memberId를 반환
    }

    public String getPassword() {
        return this.memberVo.getPassword();
    }

    public String getName() {
        return this.memberVo.getName();
    }

    public String getEmail() {
        return this.memberVo.getEmail();
    }

    public boolean isSocial() {
        return this.memberVo.getSocial() == 1;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

}
