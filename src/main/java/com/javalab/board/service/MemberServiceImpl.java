package com.javalab.board.service;

import com.javalab.board.repository.MemberMapper;
import com.javalab.board.repository.UserRolesMapper;
import com.javalab.board.vo.MemberVo;
import com.javalab.board.vo.UserRolesVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final UserRolesMapper userRolesMapper;

    @Override
    @Transactional
    public void registerMember(MemberVo memberVo, UserRolesVo userRolesVo, MultipartFile file) throws Exception {
        try {
            if (file != null && !file.isEmpty()) {
                String uploadDir = "C:\\filetest\\upload";
                Path uploadPath = Paths.get(uploadDir);
                Files.createDirectories(uploadPath);

                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                memberVo.setFileName(fileName);
                memberVo.setFilePath(filePath.toString());
            }

            memberMapper.insertMember(memberVo);
            userRolesMapper.insertUserRole(userRolesVo);
        } catch (Exception e) {
            log.error("회원 등록 중 오류 발생: {}", e.getMessage());
            throw new Exception("회원 등록 실패", e);
        }
    }

    @Override
    @Transactional
    public void registerMember(MemberVo memberVo, UserRolesVo userRolesVo) throws Exception {
        try {
            memberMapper.insertMember(memberVo);
            userRolesMapper.insertUserRole(userRolesVo);
        } catch (Exception e) {
            log.error("회원 등록 중 오류 발생: {}", e.getMessage());
            throw new Exception("회원 등록 실패", e);
        }
    }

    @Override
    public MemberVo findMemberByEmail(String email) {
        return memberMapper.findMemberByEmail(email);
    }


    // 필요한 추가 메서드 구현

///**
//     * 소셜 로그인 회원의 비밀번호 및 소셜 로그인 상태 수정
//     * @param email
//     * @param encodedPassword
//     */
//    @Override
//    public void modifyPasswordAndSocialStatus(String email,
//                                              String encodedPassword) {
//
//        memberMapper.modifyPasswordAndSocialStatus(email, encodedPassword);
//    }
//
//    /**
//     * 소셜로그인 회원 정보 저장시 사용
//     * - 회원 정보 저장
//     * - 회원 역할 저장
//     * @param member
//     */
//    @Override
//    public void saveMemberWithRole(MemberVo member) {
//        log.info("saveMemberWithRole....{}", member);
//
//        // 사용자 저장
//        memberMapper.save(member);
//
//        // 회원의 역할 저장
//        if (!member.getRoles().isEmpty()) {
//            Role role = member.getRoles().get(0);  // 첫 번째 역할만 저장
//            memberMapper.saveRole(member.getMemberId(), role.getRoleId());
//        }
//    }
//
//    /**
//     * 소셜로그인의 경우  이메일을 통해 회원 정보 조회
//     * @param email
//     * @return
//     */
//    @Override
//    public MemberVo findMemberByEmail(String email) {
//        return memberMapper.login(email);
//    }
//
//    @Override
//    public MemberVo findMemberById(String memberId) {
//        return memberMapper.findMemberById(memberId);
//    }
//
//    @Override
//    public List<MemberVo> findAllMembers() {
//        return memberMapper.findAllMembers();
//    }
//
//    @Override
//    public void updateMember(MemberFormDto memberFormDto) {
//        // DTO를 VO로 변환
//        MemberVo memberVo = modelMapper.map(memberFormDto, MemberVo.class);
//        memberMapper.update(memberVo);
//    }
//
//    @Override
//    public void deleteMember(String memberId) {
//        memberMapper.delete(memberId);
//    }
}