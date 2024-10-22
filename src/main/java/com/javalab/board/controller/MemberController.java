package com.javalab.board.controller;

import com.javalab.board.service.MemberService;
import com.javalab.board.vo.MemberVo;
import com.javalab.board.vo.UserRolesVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/classification")
    public String classificationPage() {
        return "member/classification";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("memberVo", new MemberVo());
        return "member/join";
    }

    @PostMapping("/join")
    public String registerMember(@Valid @ModelAttribute("memberVo") MemberVo memberVo,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "file", required = false) MultipartFile file,
                                 RedirectAttributes redirectAttributes) {

        log.info("회원가입 요청: {}", memberVo);

        if (!memberVo.getPassword().equals(memberVo.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "비밀번호가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            log.error("유효성 검사 오류: {}", bindingResult.getAllErrors());
            return "member/join";
        }

        memberVo.setPassword(passwordEncoder.encode(memberVo.getPassword()));

        UserRolesVo userRolesVo = new UserRolesVo();
        userRolesVo.setUserId(memberVo.getMemberId());
        userRolesVo.setUserType("person");
        userRolesVo.setRoleId("ROLE_USER");

        try {
            memberService.registerMember(memberVo, userRolesVo, file);
            redirectAttributes.addFlashAttribute("message", "개인 회원가입이 성공적으로 완료되었습니다.");
            return "redirect:/member/login.do";
        } catch (Exception e) {
            log.error("회원가입 처리 중 오류 발생", e);
            bindingResult.reject("registerError", "회원가입 처리 중 오류가 발생했습니다: " + e.getMessage());
            return "member/join";
        }
    }


//    // 회원정보 수정 페이지
//    @GetMapping("/modify")
//    public String showModifyPage(@AuthenticationPrincipal Object principal, Model model) {
//        if (principal instanceof MemberVo) {
//            MemberVo memberVo = (MemberVo) principal;
//            model.addAttribute("name", memberVo.getName());
//            model.addAttribute("birth", memberVo.getBirth());
//            model.addAttribute("tel", memberVo.getTel());
//            model.addAttribute("address", memberVo.getAddress());
//        }
//        return "member/modify"; // 회원정보 수정 페이지
//    }
//
//    // 회원정보 수정 처리
//    @PostMapping("/modify")
//    public String modifyUser(
//            @AuthenticationPrincipal Object principal,
//            @RequestParam(name = "newPassword", required = false) String newPassword,
//            @RequestParam(name = "name", required = false) String name,
//            @RequestParam(name = "birth", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date birth,
//            @RequestParam(name = "tel", required = false) String tel,
//            @RequestParam(name = "address", required = false) String address) {
//
//        if (principal instanceof MemberVo) {
//            MemberVo member = (MemberVo) principal;
//
//            // 비밀번호 변경 처리
//            if (newPassword != null && !newPassword.isEmpty()) {
//                member.setPassword(passwordEncoder.encode(newPassword)); // 비밀번호 암호화
//            }
//
//            // 사용자 정보 수정
//            if (name != null && !name.isEmpty()) member.setName(name);
//            if (birth != null) member.setBirth(birth);
//            if (tel != null && !tel.isEmpty()) member.setTel(tel);
//            if (address != null && !address.isEmpty()) member.setAddress(address);
//
//            // 수정된 정보를 데이터베이스에 반영
//            loginMapper.updateMember(member); // Member 정보를 업데이트
//        }
//
//        // 성공적으로 처리 후 리다이렉트
//        return "redirect:/index"; // 수정 후 리다이렉트할 페이지
//    }
}
