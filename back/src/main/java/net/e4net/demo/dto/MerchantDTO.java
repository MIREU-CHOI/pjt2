package net.e4net.demo.dto;

import lombok.Builder;
import lombok.Data;
import net.e4net.demo.entity.Member;

@Data
public class MerchantDTO {

	private Long merchantSn;	// 가맹점번호
	private Member member;		// 회원번호
	private String merchantNm;	// 가맹점명
	private String merchantDesc;// 가맹점 설명
	private String merchantUrl;	// 홈페이지URL
	private String telNo;		// 전화번호
	private String emailAddr;	// 이메일주소
	private String zipCd;		// 우편번호
	private String zipAddr;		// 우편번호주소
	private String detailAddr;	// 상세주소
	
	public MerchantDTO() {
		
	}
	
	@Builder
	public MerchantDTO(Long merchantSn, Member member, String merchantNm, String merchantDesc, String merchantUrl,
			String telNo, String emailAddr, String zipCd, String zipAddr, String detailAddr) {
		super();
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
