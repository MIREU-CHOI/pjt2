package net.e4net.demo.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.e4net.demo.entity.Member;
import net.e4net.demo.entity.MembCls;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDTO { // Request를 받을 때 쓰이는 dto
	private String membId;
    private String membPwd;
    private String zipCd;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .membId(membId)
                .membPwd(passwordEncoder.encode(membPwd))
                .zipCd(zipCd)
                .membCls(MembCls.ROLE_USER)
                .build();
    }

    // 아이디와 비밀번호가 일치하는지 검증하는 로직
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(membId, membPwd);
    }
}
