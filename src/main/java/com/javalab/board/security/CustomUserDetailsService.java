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

        PersonVo personVo = personMapper.findPersonById(personId);

        if (personVo == null) {
            throw new UsernameNotFoundException(personId);
        }
        return new CustomUser(personVo);
    }
}
