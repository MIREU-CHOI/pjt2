package net.e4net.demo.service;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.e4net.demo.entity.Member;
import net.e4net.demo.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    // 받은 membId을 통해 user가 실제로 존재하는지 알아보는 메소드. 존재하지 않으면 예외.
    @Override
    public UserDetails loadUserByUsername(String membId) throws UsernameNotFoundException {
        return memberRepository.findByMembId(membId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(membId + " 을 DB에서 찾을 수 없습니다"));
    }

    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getMembCls().toString());

        return new User(
                String.valueOf(member.getMembId()),
                member.getMembPwd(),
                Collections.singleton(grantedAuthority)
        );
    }
}