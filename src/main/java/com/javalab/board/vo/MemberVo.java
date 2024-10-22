package com.javalab.board.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map; // Map import 추가

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class MemberVo {

	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]{5,}$", message = "아이디는 영문 및 숫자를 포함하여 5자 이상이어야 합니다.")
	private String memberId; // member_id에 매핑

	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "올바른 이메일 형식을 입력해 주세요.")
	private String email; // email에 매핑

	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,}$", message = "비밀번호는 대소문자, 숫자 및 특수문자를 포함하여 5자 이상이어야 합니다.")
	private String password; // password에 매핑

	@NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
	private String confirmPassword; // 비밀번호 확인 필드 (DB 매핑 없음)

	@NotBlank(message = "이름은 필수 입력 값입니다.")
	@Pattern(regexp = "^[가-힣A-Za-z]{2,}$", message = "이름은 한글 또는 영문으로 두 글자 이상이어야 합니다.")
	private String name; // name에 매핑

	@NotNull(message = "생년월일은 필수 입력 값입니다.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth; // birth에 매핑

	@NotBlank(message = "전화번호는 필수 입력 값입니다.")
	@Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 '-'를 제외한 10자리 또는 11자리 숫자만 입력해야 합니다.")
	private String tel; // tel에 매핑

	private String fileName; // file_name에 매핑
	private String filePath; // file_path에 매핑

	@NotBlank(message = "주소는 필수 입력 값입니다.")
	private String address; // address에 매핑

	private Integer locationId; // location_id에 매핑
	private Integer gradeId; // grade_id에 매핑
	private Integer transCount; // transCount에 매핑
	private Integer totalPoints; // totalPoints에 매핑

	// 역할 리스트 추가
	private List<RoleVo> roles; // roles 필드 추가

	// 소셜 로그인 등에서 사용할 추가 정보 저장을 위한 attributes 필드
	private Map<String, Object> attributes; // attributes 필드 추가

	// 소셜 로그인 사용자 여부를 확인하기 위한 필드 추가
	private Integer social; // 1: 소셜 로그인, 0: 일반 로그인
}
