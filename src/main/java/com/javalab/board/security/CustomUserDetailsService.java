package com.javalab.board.security;

import com.javalab.board.dto.CustomUser;
import com.javalab.board.dto.PersonDto;
import com.javalab.board.repository.PersonMapper;
import com.javalab.board.vo.PersonVo;
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

    private final PersonMapper personMapper;

    // 실제 인증 진행(DB에 회원 ID로 사용자 정보 조회)
    @Override
    public UserDetails loadUserByUsername(String personId) throws UsernameNotFoundException {
        log.info("로그인 시도한 personId: {}", personId); // personId가 null이거나 빈 문자열인지 확인
        PersonVo personVo = personMapper.getPersonById(personId);
        if (personVo == null) {
            log.error("사용자를 찾을 수 없습니다: {}", personId); // 에러 로그 추가
            throw new UsernameNotFoundException(personId);
        }
        log.info("사용자 정보 조회 성공: {}", personVo); // 사용자 정보 확인
        return new CustomUser(personVo);
    }

}
