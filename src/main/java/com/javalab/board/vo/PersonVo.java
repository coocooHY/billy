package com.javalab.board.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonVo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String personId;     // 유저 아이디
	private String email;        // 이메일
	private String password;     // 비밀번호
	private String name;         // 이름
	private Date birth;          // 생년월일
	private String tel;          // 연락처
	private String fileName;     // 파일명
	private String filePath;     // 파일 주소
	private String address;      // 주소
	private int locationId;      // 지역 ID
	private int gradeId;         // 등급 ID
	private int transCount;      // 거래 회수
	private int totalPoints;     // 총 보유 포인트

	@Builder.Default
	private List<Role> roles = new ArrayList<>(); // MemberRole 객체 리스트

	private Map<String, Object> attributes = Map.of(); // 소셜 로그인 정보 기본 값 설정




//	// 필요시 추가적인 메소드
//	public boolean isDeleted() {
//		return this.del == 1;
//	}
//
//	public boolean isSocialUser() {
//		return this.social == 1;
//	}

}
