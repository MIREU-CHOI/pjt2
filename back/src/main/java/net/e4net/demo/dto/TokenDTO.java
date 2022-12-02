package net.e4net.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.e4net.demo.entity.MembCls;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;
    
    private Long membSn;	// 회원번호			
    private MembCls membCls;// 회원구분 - ROLE_ADMIN:어드민, ROLE_SELLER:판매자, ROLE_USER: 사용자
    private String membId;	// 회원 ID
    
}
