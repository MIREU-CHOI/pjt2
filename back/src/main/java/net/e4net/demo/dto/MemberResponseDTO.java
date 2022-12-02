package net.e4net.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.e4net.demo.entity.MembCls;
import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.Member;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDTO { // Response를 보낼때 쓰이는 dto

//	private String membId;
//	private String zipCd;
    
    // 미르 추가 
    private Long membSn;		// 회원번호	
	private MembCls membCls;  // 회원구분 - ROLE_ADMIN:어드민, ROLE_SELLER:판매자, ROLE_USER: 사용자
	private String membStatusCd;// 회원상태코드 - 10:가입, 20:휴면, 99:탈퇴
	private String membId;		// 회원 ID
	private String membPwd;		// 회원 PWD
	private String membNm;		// 회원성명
	private String mobileNo;	// 휴대폰번호
	private String emailAddr;	// 이메일주소
	private String zipCd;		// 우편번호
	private String zipAddr;		// 우편번호주소
	private String detailAddr;	// 상세주소
	private String lastLoginDtm;// 최종 로그인 일시
	private Money membMoney;// 회원 머니 entity

//    public static MemberResponseDTO of(Member member) {
//        return MemberResponseDTO.builder()
//                .membId(member.getMembId())
//                
//                .zipCd(member.getZipCd())
//                .build();
//    }
    

    
}
