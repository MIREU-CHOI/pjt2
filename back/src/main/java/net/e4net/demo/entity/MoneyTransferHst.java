package net.e4net.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TB_MONEY_TRANSFER_HST")
@SequenceGenerator(
		name = "TRANSFER_HST_SEQ_GENERATOR",
		sequenceName = "TRANSFER_HST_SEQ", 
		initialValue = 1,
		allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoneyTransferHst extends CommonData{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSFER_HST_SEQ_GENERATOR")
	@Column(name = "MONEY_TRANSFER_HST_SN")
	private Long moneyTransferHstSn;// 머니거래이력일련번호 , length = 10
	
	@ManyToOne
	@JoinColumn(name = "MEMB_SN", referencedColumnName = "MEMB_SN")
	private Member member;			// 회원번호
	
	@Column(name = "TRANSFER_TY_CD", nullable = false)
	private String transferTyCd;	// 거래종류코드 (01:충전, 02:사용, 03:환전) , length = 2 
	
	@Column(name = "TRANSFER_AMT", nullable = false)
	private Long transferAmt;		// 거래금액 , length = 15
	
	@Column(name = "PAY_MEAN_CD")
	private String payMeanCd;// 결제수단코드 - 충전 : 01:카드, 02:계좌이체, 03: 머니사용 , length = 2
	
	@Column(name = "PAY_TRANSFER_NO")
	private String payTranserNo;	// 결제거래번호 , length = 20
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY) //name : 내 컬럼, referencedColumnName: 내가 참조하는 컬럼 
	@JoinColumn(name = "BUY_HST_SN", referencedColumnName = "BUY_HST_SN", 
		updatable = false, insertable = true) //조인하는 컬럼 이름 
	private BuyHst buyHst;

	
	

	@Builder
	private MoneyTransferHst(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
			Timestamp lastRegistDt, Long moneyTransferHstSn, Member member, String transferTyCd, Long transferAmt,
			String payMeanCd, String payTranserNo, BuyHst buyHst) {
		super("Y", frstRegistMembSn, frstRegistDt, lastRegistMembSn, lastRegistDt);
		this.moneyTransferHstSn = moneyTransferHstSn;
		this.member = member;
		this.transferTyCd = transferTyCd;
		this.transferAmt = transferAmt;
		this.payMeanCd = payMeanCd;
		this.payTranserNo = payTranserNo;
		this.buyHst = buyHst;
		if(buyHst != null) {	// 구매이력 pk 를 가져와서 set
			buyHst.setMoneyTransferHst(this);
		}
	}
	
	
}
