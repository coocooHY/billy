package com.javalab.board.controller;

import com.javalab.board.dto.PersonDto;
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
import org.springframework.web.bind.annotation.*;
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


    @PostMapping(value = "/action.do")
    public String login(@ModelAttribute PersonDto personDto, Model model) {
        PersonVo person = personService.login(personDto.getPersonId(), personDto.getPassword());
        if (person != null) {
            model.addAttribute("person", person);
            return "redirect:/index";  // 로그인 성공 시 홈으로 리다이렉트
        } else {
            model.addAttribute("error", "Invalid ID or Password");
            return "login";  // 로그인 실패 시 다시 로그인 페이지로
        }
    }

}
