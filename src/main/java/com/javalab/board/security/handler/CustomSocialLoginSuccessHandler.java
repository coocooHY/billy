package com.javalab.board.security.handler;

import com.javalab.board.dto.CustomUser;
import com.javalab.board.dto.SocialMemberDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {

        log.info("----------------------------------------------------------");
        log.info("여기는 CustomSocialLoginSuccessHandler onAuthenticationSuccess ..........");
        log.info(authentication.getPrincipal());

        // 시큐리티에 보관한 정보 추출
        //SocialMemberDto socialMember = (SocialMemberDto) authentication.getPrincipal();
        CustomUser socialMember = (CustomUser) authentication.getPrincipal();

        log.info("socialMember.isSocial() : " + socialMember.isSocial());
        boolean boolPass = passwordEncoder.matches("1111", socialMember.getPassword());
        log.info("passwordEncoder.matches(1111) 맞나? : " + boolPass);

        // 비밀번호가 1111이라는 얘기는 소셜로그인 했으며 아직 바꿨다는 의미
        if (socialMember.isSocial() && (socialMember.getPassword().equals("1111")
                || passwordEncoder.matches("1111", socialMember.getPassword()))) {
            log.info("회원정보 수정화면으로 이동합니다. ");
            response.sendRedirect("/member/modify.do");
            return;
        } else {    // 비밀번호가 1111이 아니라는 얘기는 바꿨다는 의미
            log.info("비밀번호가 1111이 아니므로 초기 화면으로 이동합니다.");
            response.sendRedirect("/");
        }
    }
}
