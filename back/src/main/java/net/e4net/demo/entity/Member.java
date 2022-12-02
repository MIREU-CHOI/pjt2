package net.e4net.demo.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "TB_MEMB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SequenceGenerator(
		name = "MEMBER_SEQ_GENERATOR",
		sequenceName = "MEMBER_SEQ", // 매핑할 데이터베이스 시퀀스 이름
		initialValue = 1,
		allocationSize = 1)
public class Member extends CommonData{
	
	@Id  // pk
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
	@Column(name = "MEMB_SN", nullable = false) // 굳이 먼저 길이를 정할 필욘 없다! 자릿수 보여주려고 하는 정도지
	private Long membSn; // 회원번호 , length = 10
	
//	@Column(name = "MEMB_CLS", length = 20, nullable = false)
//	private String membCls; // 회원구분 - enum 써서 해보기
	@Column(name = "MEMB_CLS", nullable = false)
    @Enumerated(EnumType.STRING) 
    private MembCls membCls; // 회원구분, length = 20
	
	@Column(name = "MEMB_STATUS_CD")
	private String membStatusCd; // 회원상태코드 => 10:가입, 20:휴면, 99:탈퇴 
								// , length = 2
	
	@Column(name = "MEMB_ID", nullable = false)
	private String membId; // 회원 ID length = 12, 
	
	@Column(name = "MEMB_PWD", nullable = false)
	private String membPwd; // 회원 PWD length = 20, 
	
	@Column(name = "MEMB_NM", nullable = false)
	private String membNm;  // 회원 성명 length = 100, 
	
	@Column(name = "MOBILE_NO")
	private String mobileNo; // 휴대폰번호 length = 30
	
	@Column(name = "EMAIL_ADDR")
	private String emailAddr; // 이메일주소 , length = 100
	
	@Column(name = "ZIP_CD")
	private String zipCd;  // 우편번호 , length = 6
	
	@Column(name = "ZIP_ADDR")
	private String zipAddr; // 우편번호주소, length = 150
	
	@Column(name = "DETAIL_ADDR")
	private String detailAddr; // 상세주소 , length = 150
	
	@Column(name = "LAST_LOGIN_DTM")
	private String lastLoginDtm; //최종로그인일시 , length = 50
	
//	@Column(name = "USE_YN", length = 1)
//	private String useYn;

	// mappedBy = "member"  :  
	@OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST) // 엔티티 생성될 때 영향을 주겠다? 
	private Money membMoney;
	

	// PWD 변경 시 setter
    public void setMembPwd(String membPwd) { 
    	this.membPwd = membPwd; 
    }
    // 우편번호 변경 시 setter
    public void setZipCd(String zipCd) {
    	this.zipCd = zipCd;
    }
    
	@Builder
    public Member(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
			Timestamp lastChangeDt, Long membSn, MembCls membCls, String membStatusCd, String membId, String membPwd,
			String membNm, String mobileNo, String emailAddr, String zipCd, String zipAddr, String detailAddr,
			String lastLoginDtm, Money membMoney) {
		super(useYn, frstRegistMembSn, frstRegistDt, lastRegistMembSn, lastChangeDt);
		this.membSn = membSn;
		this.membCls = membCls;
		this.membStatusCd = membStatusCd;
		this.membId = membId;
		this.membPwd = membPwd;
		this.membNm = membNm;
		this.mobileNo = mobileNo;
		this.emailAddr = emailAddr;
		this.zipCd = zipCd;
		this.zipAddr = zipAddr;
		this.detailAddr = detailAddr;
		this.lastLoginDtm = lastLoginDtm;
		this.membMoney = membMoney;
	}
	
//	@Builder
//	public Member(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
//			Timestamp lastRegistDt, Long membSn, UserRole membCls, String membStatusCd, String membId, String membPwd,
//			String membNm, String mobileNo, String emailAddr, String zipCd, String zipAddr, String detailAddr,
//			String lastLoginDtm, String useYn2, MembMoney membMoney) {
//		super(useYn, frstRegistMembSn, frstRegistDt, lastRegistMembSn, lastRegistDt);
//		this.membSn = membSn;
//		this.membCls = membCls;
//		this.membStatusCd = membStatusCd;
//		this.membId = membId;
//		this.membPwd = membPwd;
//		this.membNm = membNm;
//		this.mobileNo = mobileNo;
//		this.emailAddr = emailAddr;
//		this.zipCd = zipCd;
//		this.zipAddr = zipAddr;
//		this.detailAddr = detailAddr;
//		this.lastLoginDtm = lastLoginDtm;
//		this.membMoney = membMoney;
//	}

	// 교육 - 예시 ===========================================
//	@Builder
//	private Member(String membCls) {
//		super(1L,2L);
//		this.membCls = "ROLE_ADMIN"; // DTO.getRoleAdmin  나중에 뷰단에서 받아와서 이런 식으로 코딩
//	}
	
	// 무조건 이 빌드패턴을 이용해야만 하도록 함 
//	public static Member createMember(String membCls) {
//		return Member.builder()
//				.membCls(membCls)
//				.build();
//	}
	
	public void setMembMoney(Money membMoney) {
		this.membMoney = membMoney;
	}
	
	// default 값 넣어주기 
	@PrePersist
	public void setting() {
//		this.membCls = MembCls.ROLE_USER; // db 컬럼명 : memb_cls
		this.membStatusCd = "10"; 	// 가입상태코드 - 10:가입, 20:휴면, 99:탈퇴
//		this.mobileNo = "01012341234";
//		this.emailAddr = "test@naver.com";
//		this.zipCd = "12345";
//		this.zipAddr = "서울시 강남구";
//		this.detailAddr = "777";
//		this.lastLoginDtm = "20221111170203";
	}
	





	
	
	
}







