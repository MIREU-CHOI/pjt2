package net.e4net.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.e4net.demo.entity.Merchant;

@Data
//@AllArgsConstructor
public class GoodsDTO {

	private String goodsNo;		// 상품번호
	private Merchant merchant;	// 가맹점번호
	private String goodsNm;		// 상품명
	private String goodsModelNo;// 상품모델번호
	private Long goodsAmt;		// 상품 금액
	private Long goodsQtt;		// 상품 수량
	private Long goodsSellQtt;	// 판매 수량
	private String goodsClsDt;	// 판매종료일자
	private String goodsShppCost;// 배송비용
	private String realFileNm;	// 실제파일명
	private String goodsImgPath;// 상품이미지 경로
	private String goodsDesc;	// 상품 설명

	public GoodsDTO() {
	}

	@Builder
	public GoodsDTO(String goodsNo, Merchant merchant, String goodsNm, String goodsModelNo, Long goodsAmt,
			Long goodsQtt, Long goodsSellQtt, String goodsClsDt, String goodsShppCost, String realFileNm,
			String goodsImgPath, String goodsDesc) {
		super();
		this.goodsNo = goodsNo;
		this.merchant = merchant;
		this.goodsNm = goodsNm;
		this.goodsModelNo = goodsModelNo;
		this.goodsAmt = goodsAmt;
		this.goodsQtt = goodsQtt;
		this.goodsSellQtt = goodsSellQtt;
		this.goodsClsDt = goodsClsDt;
		this.goodsShppCost = goodsShppCost;
		this.realFileNm = realFileNm;
		this.goodsImgPath = goodsImgPath;
		this.goodsDesc = goodsDesc;
	}
	
	
	
	
	
	
	
}
