package net.e4net.demo.dto;

import lombok.Data;
import net.e4net.demo.entity.Goods;
import net.e4net.demo.entity.Member;

@Data
public class BuyHstDTO {

	private Long buyHstSn;	// 구매이력일련번호
	private Member member;	// 회원번호
	private Goods goods;	// 상품번호
	private Long goodsAmt;	// 상품금액
	private Long buyQtt;	// 구매수량
	private Long buyAmt;	// 구매금액
//	private Long payTranserNo;

}
