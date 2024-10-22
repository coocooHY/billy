package com.javalab.board.controller;

import com.javalab.board.vo.BoardVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        // 홈 페이지에서 필요한 모델 속성 추가
        model.addAttribute("officeTitle", "Our Office");
        model.addAttribute("officeAddress", "123 Street, New York, USA");
        model.addAttribute("emailTitle", "Email Us");
        model.addAttribute("emailAddress", "info@example.com");
        model.addAttribute("phoneTitle", "Call Us");
        model.addAttribute("phoneNumber", "+012 345 6789");

        // About 섹션에 필요한 모델 속성 추가
        model.addAttribute("aboutTitle", "About Us");
        model.addAttribute("aboutSubtitle", "We Keep Your Pets Happy All Time");
        model.addAttribute("aboutDescription", "Diam dolor diam ipsum tempor sit.");
        model.addAttribute("missionTitle", "Our Mission");
        model.addAttribute("missionDescription", "뚱이랑 루이는 행복하다");
        model.addAttribute("visionTitle", "Our Vision");
        model.addAttribute("visionDescription", "뚱이랑 루이는 사랑스럽다");

        return "index"; // src/main/resources/templates/index.html 타임리프 페이지
    }

    @GetMapping("/ex01")
    public String ex01(Model model) {
        BoardVo boardVo = BoardVo.builder()
                .bno(1)
                .title("제목1")
                .content("내용1")
                .memberId("java")
                .regDate(new Date())
                .build();

        model.addAttribute("boardVo", boardVo);
        return "ex/ex01"; // src/main/resources/templates/ex/ex01.html 타임리프 페이지
    }
}
