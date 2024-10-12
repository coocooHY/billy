package com.javalab.board.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Setter
@ToString
@Builder
public class MemberVo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String memberId;
	private String password;
	private String name;
	private String email;
	private int point = 0;  // 포인트 점수 필드 기본 값 설정
	private int del = 0; // 기본 값 설정 (0 = 사용, 1 = 삭제)
	private int social = 0; // 기본 값 설정 (0 = 일반, 1 = 소셜)

	@Builder.Default
	private List<Role> roles = new ArrayList<>(); // MemberRole 객체 리스트

	private Map<String, Object> attributes = Map.of(); // 소셜 로그인 정보 기본 값 설정

	public MemberVo() {
	}

	// 모든 필드를 포함하는 생성자 추가
	public MemberVo(String memberId,
					String password,
					String name,
					String email,
					int point,
					int del,
					int social,
					List<Role> roles,
					Map<String, Object> attributes) {

		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.point = point;
		this.del = del;
		this.social = social;
		this.roles = roles;
		this.attributes = attributes;
	}

	// 필요시 추가적인 메소드
	public boolean isDeleted() {
		return this.del == 1;
	}

	public boolean isSocialUser() {
		return this.social == 1;
	}

}
