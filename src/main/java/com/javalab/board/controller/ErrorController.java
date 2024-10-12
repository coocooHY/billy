package com.javalab.board.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 접근 권한이 없을 경우 처리 컨트롤러
 */
@Controller
@Slf4j
public class ErrorController {

    @GetMapping("/access-denied")
    public String accessDenied(Model model, HttpServletResponse response) {

        model.addAttribute("error", "접근 권한이 없습니다.");

        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 상태 코드 설정
        return "error/access-denied";
    }
}
