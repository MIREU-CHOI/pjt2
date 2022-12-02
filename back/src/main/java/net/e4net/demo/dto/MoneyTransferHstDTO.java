package net.e4net.demo.dto;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.e4net.demo.entity.BuyHst;
import net.e4net.demo.entity.Member;
import net.e4net.demo.entity.MoneyTransferHst;

@Getter
@Setter
public class MoneyTransferHstDTO {
	private Long moneyTransferHstSn;// 머니거래이력일련번호
	private Long membSn;
	private Member member;			// 회원번호
	private String transferTyCd;	// 거래종류코드 (01:충전, 02:사용, 03:환전)
	private Long transferAmt;		// 거래금액
	private String payMeanCd;		// 결제수단코드 - 충전 : 01:카드, 02:계좌이체, 03: 머니사용
	private String payTranserNo;	// 결제거래번호
	private BuyHst buyHst;
	private String goodsNm;
	private String merchantNm;
	private Timestamp frstRegistDt; 
	
	// 거래내역 페이지를 위한 entity -> toDto 
	public static MoneyTransferHstDTO toDto(MoneyTransferHst hst) {
		return MoneyTransferHstDTO.builder()
			.membSn(hst.getMember().getMembSn())
			.moneyTransferHstSn(hst.getMoneyTransferHstSn())
			// 거래내역 컬럼
			.frstRegistDt(hst.getFrstRegistDt())
			.transferTyCd(hst.getTransferTyCd())
			.payMeanCd(hst.getPayMeanCd())
			.goodsNm(hst.getBuyHst()==null ? null : hst.getBuyHst().getGoods().getGoodsNm())
			.merchantNm(hst.getBuyHst()==null ? null :hst.getBuyHst().getGoods().getMerchant().getMerchantNm())
			.transferAmt(hst.getTransferAmt())
			.build();
	}
	
	public MoneyTransferHstDTO () {
	}
	
	@Builder
	public MoneyTransferHstDTO(Long moneyTransferHstSn, Long membSn, Member member, String transferTyCd,
			Long transferAmt, String payMeanCd, String payTranserNo, BuyHst buyHst, String goodsNm, String merchantNm,
			Timestamp frstRegistDt) {
		this.moneyTransferHstSn = moneyTransferHstSn;
		this.membSn = membSn;
		this.member = member;
		this.transferTyCd = transferTyCd;
		this.transferAmt = transferAmt;
		this.payMeanCd = payMeanCd;
		this.payTranserNo = payTranserNo;
		this.buyHst = buyHst;
		this.goodsNm = goodsNm;
		this.merchantNm = merchantNm;
		this.frstRegistDt = frstRegistDt;
	}
	
	
	
}
