package com.javalab.board.service;

import com.javalab.board.dto.MemberFormDto;
import com.javalab.board.repository.MemberMapper;
import com.javalab.board.service.MemberService;
import com.javalab.board.vo.MemberVo;
import com.javalab.board.vo.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final ModelMapper modelMapper;

    /**
     * 회원 저장시 사용
     * - 회원 정보 저장
     * - 회원 역할 저장, 회원 역할은 기본적으로 'ROLE_USER'로 저장
     * @param memberFormDto
     */
    @Override
    public void saveMember(MemberFormDto memberFormDto) {
        // modelMapper를 이용해서 DTO를 VO로 쉽게 변환
        MemberVo memberVo = modelMapper.map(memberFormDto, MemberVo.class);
        // 회원 저장
        memberMapper.save(memberVo);
        // 회원 역할 저장
        memberMapper.saveRole(memberVo.getMemberId(), 1); // 권한은 1, ROLE_USER 하드코딩
    }


    /**
     * 소셜 로그인 회원의 비밀번호 및 소셜 로그인 상태 수정
     * @param email
     * @param encodedPassword
     */
    @Override
    public void modifyPasswordAndSocialStatus(String email,
                                              String encodedPassword) {

        memberMapper.modifyPasswordAndSocialStatus(email, encodedPassword);
    }

    /**
     * 소셜로그인 회원 정보 저장시 사용
     * - 회원 정보 저장
     * - 회원 역할 저장
     * @param member
     */
    @Override
    public void saveMemberWithRole(MemberVo member) {
        log.info("saveMemberWithRole....{}", member);

        // 사용자 저장
        memberMapper.save(member);

        // 회원의 역할 저장
        if (!member.getRoles().isEmpty()) {
            Role role = member.getRoles().get(0);  // 첫 번째 역할만 저장
            memberMapper.saveRole(member.getMemberId(), role.getRoleId());
        }
    }

    /**
     * 소셜로그인의 경우  이메일을 통해 회원 정보 조회
     * @param email
     * @return
     */
    @Override
    public MemberVo findMemberByEmail(String email) {
        return memberMapper.login(email);
    }

    @Override
    public MemberVo findMemberById(String memberId) {
        return memberMapper.findMemberById(memberId);
    }

    @Override
    public List<MemberVo> findAllMembers() {
        return memberMapper.findAllMembers();
    }

    @Override
    public void updateMember(MemberFormDto memberFormDto) {
        // DTO를 VO로 변환
        MemberVo memberVo = modelMapper.map(memberFormDto, MemberVo.class);
        memberMapper.update(memberVo);
    }

    @Override
    public void deleteMember(String memberId) {
        memberMapper.delete(memberId);
    }
}
