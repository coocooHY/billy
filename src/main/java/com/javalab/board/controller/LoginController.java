package com.javalab.board.controller;

import com.javalab.board.dto.PersonFormDto;
import com.javalab.board.service.PersonService;
import com.javalab.board.service.PersonService;
import com.javalab.board.vo.PersonVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;


    // 로그인 화면
    @GetMapping(value = "/login.do")
    public String login(Model model,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception){
        log.info("PersonController loginMember 메소드");

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "member/login";
    }


    @PostMapping(value = "/login.do")
    public String login(Model model, Authentication authentication) {
        // 인증된 사용자의 ID (username) 가져오기
        String personId = authentication.getName();

        // 사용자의 정보를 가져오기
        PersonVo person = personService.findPersonById(personId);

        // 사용자 정보가 없을 경우 예외 처리
        if (person == null) {
            throw new IllegalArgumentException("User not found with username: " + personId);
        }

        // 조회한 사용자 정보를 모델에 추가
        model.addAttribute("person", person);

        // 로그인 후 메인 페이지로 이동
        return "index"; // Thymeleaf 템플릿 파일 이름
    }


}
