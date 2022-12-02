package net.e4net.demo.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
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
@Table(name = "TB_BUY_HST")
@SequenceGenerator(
		name = "BUY_HST_SEQ_GENERATOR",
		sequenceName = "BUY_HST_SEQ",
		initialValue = 1,
		allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BuyHst extends CommonData{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUY_HST_SEQ_GENERATOR")
	@Column(name = "BUY_HST_SN", length = 10)
	private Long buyHstSn;	// 구매이력일련번호

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMB_SN", referencedColumnName = "MEMB_SN")
	private Member member;	// 회원번호
	
	@JsonIgnore
	// tb_goods 테이블 컬럼 조인해야 됨!!
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GOODS_NO", referencedColumnName = "GOODS_NO", nullable = false)
	private Goods goods;	// 상품번호
	
	@Column(name = "GOODS_AMT", length = 15)
	private Long goodsAmt;	// 상품금액
	
	@Column(name = "BUY_QTT", length = 8)
	private Long buyQtt;	// 구매수량
	
	@Column(name = "BUY_AMT", length = 15)
	private Long buyAmt;	// 구매금액
	
	@OneToOne(mappedBy = "buyHst", cascade = CascadeType.PERSIST) // 구매이력 pk를 머니거래이력이 참조함
	private MoneyTransferHst moneyTransferHst;
	
	public void setMoneyTransferHst(MoneyTransferHst moneyTransferHst) {
		this.moneyTransferHst = moneyTransferHst;
	}
	
	
	@Builder
	private BuyHst(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
			Timestamp lastRegistDt, Long buyHstSn, Member member, Goods goods, Long goodsAmt, Long buyQtt,
			Long buyAmt) {
		super("Y", frstRegistMembSn, frstRegistDt, lastRegistMembSn, lastRegistDt);
		this.buyHstSn = buyHstSn;
		this.member = member;
		this.goods = goods;
		this.goodsAmt = goodsAmt;
		this.buyQtt = buyQtt;
		this.buyAmt = buyAmt;
	}
	
	// == 조회 로직 == //
	/** 전체 주문 가격 조회 **/
//	public int getTotalPrice() {
//		int totalPrice = 0;
//		for ()
//	}
	
	
	// == 생성 메소드 == //
//	public static BuyHst createBuyHst(BuyHst entity ) {
//		return BuyHst.builder().en
//	}
	
	// == 비즈니스 로직 == //
	// 주문 취소 메소드 
	
	
	
	
	
	
}
