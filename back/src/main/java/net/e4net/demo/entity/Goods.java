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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TB_GOODS")
@SequenceGenerator(
		name = "GOODS_SEQ_GENERATOR",
		sequenceName = "GOODS_SEQ", // 매핑할 데이터베이스 시퀀스 이름
		initialValue = 1,
		allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods extends CommonData{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODS_SEQ_GENERATOR")
	@Column(name = "GOODS_NO", length = 10)
	private String goodsNo;		// 상품번호
	
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MERCHANT_SN", referencedColumnName = "MERCHANT_SN")
	private Merchant merchant;	// 가맹점번호
	
	@Column(name = "GOODS_NM", length = 200, nullable = false)
	private String goodsNm;		// 상품명
	
	@Column(name = "GOODS_MODEL_NO", length = 30)
	private String goodsModelNo;// 상품모델번호
	
	@Column(name = "GOODS_AMT", length = 15)
	private Long goodsAmt;		// 상품 금액
	
	@Column(name = "GOODS_QTT", length = 8)
	private Long goodsQtt;		// 상품 수량
	
	@Column(name = "GOODS_SELL_QTT", length = 8)
	private Long goodsSellQtt;	// 판매 수량
	
	@Column(name = "GOODS_CLS_DT", length = 8)
	private String goodsClsDt;	// 판매종료일자
	
	@Column(name = "GOODS_SHPP_COST", length = 6)
	private String goodsShppCost;// 배송비용
	
	@Column(name = "REAL_FILE_NM", length = 100)
	private String realFileNm;	// 실제파일명
	
	@Column(name = "GOODS_IMG_PATH", length = 200)
	private String goodsImgPath;// 상품이미지 경로

	@Column(name = "GOODS_DESC", length = 4000)
	private String goodsDesc;	// 상품 설명

	@Builder
	private Goods(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
			Timestamp lastRegistDt, String goodsNo, Merchant merchant, String goodsNm, String goodsModelNo,
			Long goodsAmt, Long goodsQtt, Long goodsSellQtt, String goodsClsDt, String goodsShppCost, String realFileNm,
			String goodsImgPath, String goodsDesc) {
		super(useYn, frstRegistMembSn, frstRegistDt, lastRegistMembSn, lastRegistDt);
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
