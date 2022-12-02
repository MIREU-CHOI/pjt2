package net.e4net.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.e4net.demo.entity.Member;

@Getter
@Setter
public class MembLoginHstDTO {

	private Long loginSn;		// 로그인일련번호
	private Member member;		// 회원번호
	private String connectIp;	// 접속IP
	
	@Builder
	public MembLoginHstDTO(Long loginSn, Member member, String connectIp) {
		this.loginSn = loginSn;
		this.member = member;
		this.connectIp = connectIp;
	}
	
	

}
