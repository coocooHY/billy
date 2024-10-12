package com.javalab.board.dto;

import com.javalab.board.vo.MemberVo;
import com.javalab.board.vo.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Slf4j
public class MemberDto  {
    private static final long serialVersionUID = 1L;

    //private MemberVo memberVo;

//    private String memberId;
//    private String password;
//    private String name;
//    private String email;
//    private int point = 0;  // 포인트 점수 필드 기본 값 설정
//    private int del = 0; // 기본 값 설정 (0 = 사용, 1 = 삭제)
//    private int social = 0; // 기본 값 설정 (0 = 일반, 1 = 소셜)
//
//    //@Builder.Default
//    private List<Role> roles = new ArrayList<>(); // MemberRole 객체 리스트

//
//    public MemberDto(String username, String password,
//                      Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//
//        log.info("MemberDto 생성자 호출 -1 ");
//    }
//
//    public MemberDto(MemberVo memberVo) {
//        super(memberVo.getMemberId(),
//                memberVo.getPassword(),
//                memberVo.getRoles().stream()
//                        .map(g -> new SimpleGrantedAuthority(g.getRoleName()))
//                        .collect(Collectors.toList()));
//
//        log.info("MemberDto 생성자 호출 - 2");
//
//        //this.memberVo = memberVo;
//        this.memberId = memberVo.getMemberId();
//        this.password = memberVo.getPassword();
//        this.name = memberVo.getName();
//        this.email = memberVo.getEmail();
//        this.point = memberVo.getPoint();
//        this.del = memberVo.getDel();
//        this.social = memberVo.getSocial();
//        this.roles = memberVo.getRoles();
//
//    }

    // 사용자를 식별하는 값 반환
//    @Override
//    public String getUsername() {
//        //return this.memberVo.getMemberId(); // 여기서 memberId를 반환
//        return this.getMemberId(); // 여기서 memberId를 반환
//    }
//
//    // 사용자의 실제 이름 반환
//    public String getRealName() {
//        return this.name;
//    }
}
