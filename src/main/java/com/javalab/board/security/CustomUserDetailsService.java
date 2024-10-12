package com.javalab.board.security;

import com.javalab.board.dto.CustomUser;
import com.javalab.board.dto.MemberDto;
import com.javalab.board.repository.MemberMapper;
import com.javalab.board.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    // 실제 인증 진행(DB에 회원 ID로 사용자 정보 조회)
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        MemberVo memberVo = memberMapper.findMemberById(memberId);

        if (memberVo == null) {
            throw new UsernameNotFoundException(memberId);
        }
        return new CustomUser(memberVo);
    }
}
