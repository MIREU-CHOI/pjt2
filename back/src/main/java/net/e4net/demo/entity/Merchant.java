package net.e4net.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TB_MERCHANT")
@SequenceGenerator(
		name = "MERCHANT_SEQ_GENERATOR",
		sequenceName = "MERCHANT_SEQ",
		initialValue = 1,
		allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Merchant extends CommonData{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MERCHANT_SEQ_GENERATOR")
	@Column(name = "MERCHANT_SN", length = 10)
	private Long merchantSn;	// 가맹점번호

	@OneToOne   // join 할 때 서로를 참조해야 하니까
//	@JoinColumn  :  외래키를 정의하는 어노테이션 
	@JoinColumn(name = "MEMB_SN", referencedColumnName = "MEMB_SN")
	private Member member;		// 회원번호
	
	@Column(name = "MERCHANT_NM", nullable = false)
	private String merchantNm;	// 가맹점명 , length = 100
	
	@Column(name = "MERCHANT_DESC")
	private String merchantDesc;// 가맹점 설명 , length = 4000
	
	@Column(name = "MERCHANT_URL")
	private String merchantUrl;	// 홈페이지URL , length = 100
	
	@Column(name = "TEL_NO")
	private String telNo;		// 전화번호 , length = 20
	
	@Column(name = "EMAIL_ADDR")
	private String emailAddr;	// 이메일주소 , length = 100
	
	@Column(name = "ZIP_CD")
	private String zipCd;		// 우편번호 , length = 6
	
	@Column(name = "ZIP_ADDR")
	private String zipAddr;		// 우편번호주소 , length = 150
	
	@Column(name = "DETAIL_ADDR")
	private String detailAddr;	// 상세주소 , length = 150

	@Builder
	private Merchant(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
			Timestamp lastRegistDt, Long merchantSn, Member member, String merchantNm, String merchantDesc,
			String merchantUrl, String telNo, String emailAddr, String zipCd, String zipAddr, String detailAddr) {
		super(useYn, frstRegistMembSn, frstRegistDt, lastRegistMembSn, lastRegistDt);
		this.merchantSn = merchantSn;
		this.member = member;
		this.merchantNm = merchantNm;
		this.merchantDesc = merchantDesc;
		this.merchantUrl = merchantUrl;
		this.telNo = telNo;
		this.emailAddr = emailAddr;
		this.zipCd = zipCd;
		this.zipAddr = zipAddr;
		this.detailAddr = detailAddr;
	}
	
	
	
	
	

}
