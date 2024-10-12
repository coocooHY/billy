package com.javalab.board.security;

import com.javalab.board.dto.CustomUser;
import com.javalab.board.dto.SocialMemberDto;
import com.javalab.board.service.MemberService;
import com.javalab.board.vo.MemberVo;
import com.javalab.board.vo.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest....{}", userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        log.info("clientName {} ", clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String email = null;
        String name = null;

        switch (clientName) {
            case "kakao":
                email = getKakaoEmail(paramMap);
                name = getKakaoNickname(paramMap);
                break;
        }

        log.info("===============================");
        log.info("카카오에서 받아온 이메일 : " + email);
        log.info("===============================");

        OAuth2User oAuth2UserDto = generateDTO(email, name, paramMap);

        return oAuth2UserDto;
    }

    private OAuth2User generateDTO(String email, String name, Map<String, Object> params) {

        MemberVo result = memberService.findMemberByEmail(email);

        if (result == null) {
            log.info("소셜로그인 사용자가 디비에 존재하지 않습니다.");

            // UUID를 사용하여 고유한 memberId 생성
            String uuidMemberId = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode("1111");

            // 새로운 사용자 생성 및 기본 역할 설정
            Role role = new Role();
            role.setRoleId(1);  // role_id를 1로 설정 (ROLE_USER)
            role.setRoleName("ROLE_USER");
            List<Role> roles = Collections.singletonList(role);

            MemberVo member = MemberVo.builder()
                    .memberId(uuidMemberId)
                    .password(encodedPassword)
                    .name(name != null ? name : "social user")
                    .email(email)
                    .point(0)
                    .del(0)
                    .social(1)
                    .roles(roles)
                    .attributes(params)
                    .build();

            memberService.saveMemberWithRole(member);

            log.info("CustomOAuth2UserService 저장 완료후 member....{}", member);

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                    .collect(Collectors.toList());

            log.info("member.getRoles() {}", member.getRoles());


            return new CustomUser(member, params);

        } else {

            log.info("소셜로그인 사용자가 디비에 이미 존재합니다. {}", result);

            log.info("result.getRoles() {}", result.getRoles());

            return new CustomUser(result,  params);
        }
    }

    private String getKakaoEmail(Map<String, Object> paramMap) {
        log.info("KAKAO-----------------------------------------");
        Object value = paramMap.get("kakao_account");
        log.info(value);
        LinkedHashMap accountMap = (LinkedHashMap) value;
        String email = (String) accountMap.get("email");
        log.info("email..." + email);
        return email;
    }

    private String getKakaoNickname(Map<String, Object> paramMap) {
        LinkedHashMap propertiesMap = (LinkedHashMap) paramMap.get("properties");
        return (String) propertiesMap.get("nickname");
    }
}
