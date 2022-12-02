package net.e4net.demo.dto;

import java.sql.Timestamp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.e4net.demo.entity.MembCls;
import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.Member;

//@Getter @Setter
@AllArgsConstructor
@Data
public class MemberDTO {
	
	private Long membSn;		// 회원번호	
	private MembCls membCls;  	// 회원구분 - ROLE_ADMIN:어드민, ROLE_SELLER:판매자, ROLE_USER: 사용자
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
	private Money membMoney;	// 회원 머니 entity
	private String useYn;
	private Long frstRegistMembSn;
	private Timestamp frstRegistDt;
	private Long lastRegistMembSn;
	private Timestamp lastChangeDt; 
	
	
	public MemberDTO () {
		 
	}
	
	
	
	/* DTO -> Entity */
    public Member toEntity(PasswordEncoder passwordEncoder) {
    	Member member = Member.builder()
    			.membSn(membSn)
                .membCls(membCls)
                .membStatusCd(membStatusCd)
                .membId(membId)
                .membPwd(passwordEncoder.encode(membPwd))
                .membNm(membNm)
                .mobileNo(mobileNo)
                .emailAddr(emailAddr)
                .zipCd(zipCd)
                .zipAddr(zipAddr)
                .detailAddr(detailAddr)
                .lastLoginDtm(lastLoginDtm)
//                .membMoney(membMoney.)
              .frstRegistMembSn(frstRegistMembSn) //'유저카드이름',
              .lastRegistMembSn(lastRegistMembSn)  //'유저카드코드',
              .frstRegistDt(frstRegistDt)  //'유저카드코드',
              .useYn(useYn)  //'유저카드코드',
              .lastChangeDt(lastChangeDt)
                .build();
        return member;
    }
    
//    public MembMoney toEntity() {
//        return MembMoney.builder()       
//       .member(member)  // 'id',
//       .moneySn(moneySn) //'pw',
//       .frstRegistMembSn(frstRegistMembSn) //'유저카드이름',
//       .lastRegistMembSn(lastRegistMembSn)  //'유저카드코드',
//       .frstRegistDt(frstRegistDt)  //'유저카드코드',
//       .lastRegistDt(lastRegistDt)  //'유저카드코드',
//       .useYn(useYn)  //'유저카드코드',
//       .build();
//    }
    

    
    public static MemberDTO toDto(Member member) {
        return MemberDTO.builder()
                .membSn(member.getMembSn())
                .membCls(member.getMembCls())
                .membStatusCd(member.getMembStatusCd())
                .membId(member.getMembId())
                .membPwd(member.getMembPwd())
                .membNm(member.getMembNm())
                .mobileNo(member.getMobileNo())
                .zipCd(member.getMembPwd())
                .zipAddr(member.getZipAddr())
                .detailAddr(member.getDetailAddr())
                .lastLoginDtm(member.getLastLoginDtm())
                .membMoney(member.getMembMoney())
                .frstRegistMembSn(member.getFrstRegistMembSn())
                .lastRegistMembSn(member.getLastRegistMembSn())
                .frstRegistDt(member.getFrstRegistDt())
                .lastChangeDt(member.getLastChangeDt())
                .useYn(member.getUseYn())
                .build();
    }
    
   @Builder
   public MemberDTO(   
         Long membSn, MembCls membCls,String membStatusCd, String membId
         , String membPwd ,String membNm ,String mobileNo, String zipCd, String zipAddr
         , String detailAddr, String lastLoginDtm,
         Money  membMoney, Long frstRegistMembSn , Long lastRegistMembSn        
         , Timestamp frstRegistDt , Timestamp lastChangeDt , String useYn) {
       
       this.membSn = membSn;
        this.membCls =membCls;
        this.membStatusCd =membStatusCd ;
        this.membId =membId ;
        this.membPwd =membPwd; 
        this.membNm =membNm; 
        this.mobileNo = mobileNo;
        this.zipCd =zipCd ; 
        this.zipAddr =zipAddr;
        this.detailAddr =detailAddr;
        this.lastLoginDtm =lastLoginDtm;
        this.membMoney =membMoney ;
        this.frstRegistMembSn =frstRegistMembSn ;
        this.lastRegistMembSn =lastRegistMembSn ;
        this.frstRegistDt =frstRegistDt ;
        this.lastChangeDt =lastChangeDt;
        this.useYn = useYn;
    }
    // 아이디와 비밀번호가 일치하는지 검증하는 로직
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(membId, membPwd);
    }
}
