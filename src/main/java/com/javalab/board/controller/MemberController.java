package com.javalab.board.controller;

import com.javalab.board.dto.CustomUser;
import com.javalab.board.dto.MemberFormDto;
import com.javalab.board.dto.SocialMemberDto;
import com.javalab.board.service.MemberService;
import com.javalab.board.vo.MemberVo;
import com.javalab.board.vo.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 회원 목록 조회
    @GetMapping("/list.do")
    public String findAllMembers(Model model) {
        List<MemberVo> memberList = memberService.findAllMembers();
        model.addAttribute("memberList", memberList);
        return "member/memberList"; // Thymeleaf 뷰의 이름, 위의 HTML 파일에 해당
    }

    // 회원 가입 화면
    @GetMapping(value = "/join.do")
    public String memberForm(Model model){
        if (!model.containsAttribute("memberFormDto")) {
            model.addAttribute("memberFormDto", new MemberFormDto());
        }
        return "member/join";
    }

    /**
     * 회원 가입 처리
     */
    @PostMapping("/join.do")
    public String registerMember(@Valid @ModelAttribute("memberFormDto") MemberFormDto memberFormDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("회원가입 데이터 검증 오류 있음");

            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMap.put(error.getField(), error.getDefaultMessage())
            );
            redirectAttributes.addFlashAttribute("errorMap", errorMap);
            redirectAttributes.addFlashAttribute("memberFormDto", memberFormDto);
            return "redirect:/member/join.do";
        }

        try {

            Role role = new Role();
            role.setRoleId(1); // 기본 권한 ROLE_USER의 role_id를 설정
            role.setRoleName("ROLE_USER"); // 기본 권한 설정

            MemberVo member = MemberVo.builder()
                    .memberId(memberFormDto.getEmail()) // 이메일을 memberId로 사용
                    .password(passwordEncoder.encode(memberFormDto.getPassword()))
                    .name(memberFormDto.getName())
                    .email(memberFormDto.getEmail())
                    // 권한은 기본 권한으로 빌더패턴으로 생성
                    .roles(List.of(role))
                    .build();

            memberService.saveMemberWithRole(member);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/member/join.do";
        }

        return "redirect:/member/login.do";
    }

    /**
     * 사용자의 모든 정보를 수정하는 폼
     */
    @GetMapping("/update.do/{memberId}")
    public String updateForm(@PathVariable("memberId") String memberId, Model model) {
        // 처음 수정화면으로 온 경우와 수정 Post 메소드에서 온 경우 분리
        if (!model.containsAttribute("memberFormDto")) {
            MemberVo memberVo = memberService.findMemberById(memberId);
            if (memberVo != null) {
                MemberFormDto memberFormDto = new MemberFormDto();
                memberFormDto.setMemberId(memberVo.getMemberId());
                memberFormDto.setName(memberVo.getName());
                memberFormDto.setEmail(memberVo.getEmail());
                // 필요한 필드를 더 설정합니다.

                model.addAttribute("memberFormDto", memberFormDto);
            } else {
                return "redirect:/member/list.do"; // 존재하지 않는 회원의 경우 리다이렉트
            }
        }
        return "member/memberUpdate"; // 수정 폼 페이지로 이동
    }

    /**
     * 사용자의 모든 정보를 수정하는 처리
     */
    @PostMapping("/update.do/{memberId}")
    public String update(@PathVariable("memberId") String memberId,
                         @ModelAttribute("memberFormDto") @Valid MemberFormDto memberFormDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        log.info("memberFormDto: {}", memberFormDto);

        // 필드 오류 메시지를 플래시 속성에 추가
        if (bindingResult.hasErrors()) {
            log.info("bindingResult: {}", bindingResult);

            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMap.put(error.getField(), error.getDefaultMessage())
            );
            redirectAttributes.addFlashAttribute("errorMap", errorMap);
            redirectAttributes.addFlashAttribute("memberFormDto", memberFormDto);

            return "redirect:/member/update.do/" + memberId; // 회원 ID를 포함하여 리다이렉트
        }

        memberFormDto.setMemberId(memberId);
        memberService.updateMember(memberFormDto);

        return "redirect:/member/profile.do"; // 회원 정보 페이지 등으로 리다이렉트할 URL
    }


    // 카카오 소셜 로그인 사용자 비밀번호 변경 화면
    @GetMapping("/modify.do")
    public String modifyGET() {
        log.info("modify get...");
        return "member/modify";
    }

    // 카카오 소셜 로그인 사용자 비밀번호+social 변경
    @PostMapping("/modify.do")
    public String modifyPOST(@AuthenticationPrincipal CustomUser customUser,
                             @RequestParam("newPassword") String newPassword,
                             RedirectAttributes redirectAttributes) {

        log.info("여기는 컨트롤러의 비밀번호 변경 메소드 modifyPOST newPassword : " + newPassword);
        log.info("여기는 컨트롤러의 비밀번호 변경 메소드 modifyPOST customUser.getEmail() : " + customUser.getEmail());

        String encodedPassword = passwordEncoder.encode(newPassword); // 새로운 비밀번호 암호화

        // 화면에서 입력한 비밀번호 변경 및 social 상태 변경
        memberService.modifyPasswordAndSocialStatus(customUser.getEmail(), encodedPassword);

        redirectAttributes.addFlashAttribute("result", "비밀번호 변경 성공");
        return "redirect:/"; // 비밀번호 변경 후 리다이렉트할 URL을 선택합니다.
    }
}
