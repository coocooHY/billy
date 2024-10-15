package com.javalab.board.controller;

import com.javalab.board.dto.CustomUser;
import com.javalab.board.dto.PersonFormDto;
import com.javalab.board.service.PersonService;
import com.javalab.board.vo.PersonVo;
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

@RequestMapping("/person")
@Controller
@RequiredArgsConstructor
@Log4j2
public class PersonController {

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;

    // 회원 목록 조회
    @GetMapping("/list.do")
    public String findAllPersons(Model model) {
        List<PersonVo> personList = personService.findAllPersons();
        model.addAttribute("personList", personList);
        return "person/personList"; // Thymeleaf 뷰의 이름, 위의 HTML 파일에 해당
    }

    // 회원 가입 화면
    @GetMapping(value = "/join.do")
    public String personForm(Model model){
        if (!model.containsAttribute("personFormDto")) {
            model.addAttribute("personFormDto", new PersonFormDto());
        }
        return "person/join";
    }

    /**
     * 회원 가입 처리
     */
    @PostMapping("/join.do")
    public String registerPerson(@Valid @ModelAttribute("personFormDto") PersonFormDto personFormDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("회원가입 데이터 검증 오류 있음");

            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMap.put(error.getField(), error.getDefaultMessage())
            );
            redirectAttributes.addFlashAttribute("errorMap", errorMap);
            redirectAttributes.addFlashAttribute("personFormDto", personFormDto);
            return "redirect:/person/join.do";
        }

        try {

            Role role = new Role();
            role.setRoleId(1); // 기본 권한 ROLE_USER의 role_id를 설정
            role.setRoleName("ROLE_USER"); // 기본 권한 설정

            PersonVo person = PersonVo.builder()
                    .personId(personFormDto.getEmail()) // 이메일을 personId로 사용
                    .password(passwordEncoder.encode(personFormDto.getPassword()))
                    .name(personFormDto.getName())
                    .email(personFormDto.getEmail())
                    // 권한은 기본 권한으로 빌더패턴으로 생성
                    .roles(List.of(role))
                    .build();

            personService.savePersonWithRole(person);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/person/join.do";
        }

        return "redirect:/person/login.do";
    }

    /**
     * 사용자의 모든 정보를 수정하는 폼
     */
    @GetMapping("/update.do/{personId}")
    public String updateForm(@PathVariable("personId") String personId, Model model) {
        // 처음 수정화면으로 온 경우와 수정 Post 메소드에서 온 경우 분리
        if (!model.containsAttribute("personFormDto")) {
            PersonVo personVo = personService.findPersonById(personId);
            if (personVo != null) {
                PersonFormDto personFormDto = new PersonFormDto();
                personFormDto.setPersonId(personVo.getPersonId());
                personFormDto.setName(personVo.getName());
                personFormDto.setEmail(personVo.getEmail());
                // 필요한 필드를 더 설정합니다.

                model.addAttribute("personFormDto", personFormDto);
            } else {
                return "redirect:/person/list.do"; // 존재하지 않는 회원의 경우 리다이렉트
            }
        }
        return "person/personUpdate"; // 수정 폼 페이지로 이동
    }

    /**
     * 사용자의 모든 정보를 수정하는 처리
     */
    @PostMapping("/update.do/{personId}")
    public String update(@PathVariable("personId") String personId,
                         @ModelAttribute("personFormDto") @Valid PersonFormDto personFormDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        log.info("personFormDto: {}", personFormDto);

        // 필드 오류 메시지를 플래시 속성에 추가
        if (bindingResult.hasErrors()) {
            log.info("bindingResult: {}", bindingResult);

            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMap.put(error.getField(), error.getDefaultMessage())
            );
            redirectAttributes.addFlashAttribute("errorMap", errorMap);
            redirectAttributes.addFlashAttribute("personFormDto", personFormDto);

            return "redirect:/person/update.do/" + personId; // 회원 ID를 포함하여 리다이렉트
        }

        personFormDto.setPersonId(personId);
        personService.updatePerson(personFormDto);

        return "redirect:/person/profile.do"; // 회원 정보 페이지 등으로 리다이렉트할 URL
    }


    // 카카오 소셜 로그인 사용자 비밀번호 변경 화면
    @GetMapping("/modify.do")
    public String modifyGET() {
        log.info("modify get...");
        return "person/modify";
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
        personService.modifyPasswordAndSocialStatus(customUser.getEmail(), encodedPassword);

        redirectAttributes.addFlashAttribute("result", "비밀번호 변경 성공");
        return "redirect:/"; // 비밀번호 변경 후 리다이렉트할 URL을 선택합니다.
    }
}
