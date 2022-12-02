package net.e4net.demo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.dto.MoneyDTO;
import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.dto.TokenDTO;
import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.MembLoginHst;
import net.e4net.demo.entity.Member;
import net.e4net.demo.jwt.TokenProvider;
import net.e4net.demo.repository.MembLoginHstRepository;
import net.e4net.demo.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
	private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final MembLoginHstRepository membLoginHstRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberDTO signup(MemberDTO requestDto) {
    	log.debug("AuthService Layer :: Call signup Method!");
        if (memberRepository.existsByMembId(requestDto.getMembId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Member member = requestDto.toEntity(passwordEncoder);
        MemberDTO dto = MemberDTO.toDto(memberRepository.save(member));
        Money.createMembMoney(member);
        
        return dto;
    }

    public TokenDTO login(MemberDTO requestDto, String connectIp) {
    	log.debug("AuthService :: login! id: {} pwd: {} ", requestDto.getMembId(), requestDto.getMembPwd());
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        Optional<Member> member = memberRepository.findByMembId(requestDto.getMembId());
        // 로그인 이력
        Long membSn = member.get().getMembSn();
        log.debug("membSn => {}",membSn);
        membLoginHstRepository.save(MembLoginHst.createMembLoginHst(membSn, connectIp));
        return tokenProvider.generateTokenDto(authentication, member);
    }

}
