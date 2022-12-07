package net.e4net.demo.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.dto.MoneyTransferHstDTO;
import net.e4net.demo.entity.Member;
import net.e4net.demo.entity.MoneyTransferHst;
import net.e4net.demo.entity.QBuyHst;
import net.e4net.demo.entity.QGoods;
import net.e4net.demo.entity.QMember;
import net.e4net.demo.entity.QMerchant;
import net.e4net.demo.entity.QMoneyTransferHst;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuerydslRepositoryImpl implements QuerydslRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;
	private final QMoneyTransferHst mth = QMoneyTransferHst.moneyTransferHst;
	private final QBuyHst buyHst = QBuyHst.buyHst;
	private final QGoods goods = QGoods.goods;
	private final QMerchant merchant = QMerchant.merchant;
	
	private final QMember memb = QMember.member;
	
	@Override
	public List<MoneyTransferHst> findByMoneyTransferHst(Long membSn) {
		return jpaQueryFactory.selectFrom(mth)
				.leftJoin(mth.buyHst, buyHst)
				.fetchJoin()
				.leftJoin(buyHst.goods, goods)
				.fetchJoin()
				.leftJoin(goods.merchant, merchant)
				.fetchJoin()
				.where(
						mth.member.membSn
						.eq(membSn))
				.fetch();
	}

	@Override
	public Page<MoneyTransferHstDTO> getAllMoneyHst(Pageable pageable, Long membSn, int rownum) {
		log.debug("QueryDSL :: 거래내역 가져오자 회원번호=>{}, row=>{}",membSn, rownum);
		List<MoneyTransferHst> moneyHst 
				= jpaQueryFactory.selectFrom(mth)
										.leftJoin(mth.buyHst, buyHst)
										.fetchJoin()
										.leftJoin(buyHst.goods, goods)
										.fetchJoin()
										.leftJoin(goods.merchant, merchant)
										.fetchJoin()
										.where(
												mth.member.membSn
												.eq(membSn))
										.orderBy(mth.moneyTransferHstSn.desc())
										.offset(rownum).limit(pageable.getPageSize())
										.fetch();
		List<MoneyTransferHstDTO> moneyHstDto = moneyHst.stream()
										.map(MoneyTransferHstDTO::toDto).collect(Collectors.toList());
		int count = jpaQueryFactory.selectFrom(mth)
										.where(
												mth.member.membSn
												.eq(membSn))
										.fetch().size();
		log.debug("QueryDSL :: count=>{}",count);
		return new PageImpl<>(moneyHstDto, pageable, count);
	}

	@Override
	public Page<MoneyTransferHstDTO> getMoneyHstByPayMeanCd(Pageable pageable, 
			Long membSn, int rownum, String payMeanCd, String startDate, String endDate) {
		log.debug("QueryDSL :: 거래내역 \n  결제수단=>{} 날짜{}~{}", payMeanCd, startDate, endDate);
//		DateUtils du = DateUtils.getInstance();
//		String startRes = 
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		Date start=null;
		try {
			start = format.parse(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringTemplate startRes = Expressions.stringTemplate(startDate, "%Y-%m-%d");
		log.debug("start => {}",start);
		StringTemplate endRes = Expressions.stringTemplate(endDate, "%Y-%m-%d");
		log.debug("endRes => {}",endRes);
		List<MoneyTransferHst> moneyHst = new ArrayList<>();
		if (payMeanCd.equals("00")) {
			moneyHst = jpaQueryFactory.selectFrom(mth)
					.leftJoin(mth.buyHst, buyHst)
					.fetchJoin()
					.leftJoin(buyHst.goods, goods)
					.fetchJoin()
					.leftJoin(goods.merchant, merchant)
					.fetchJoin()
					.where(
							mth.member.membSn
							.eq(membSn)
							.and(
									mth.payMeanCd
									.eq(payMeanCd)
									)
							)
					.orderBy(mth.moneyTransferHstSn.desc())
					.offset(rownum).limit(pageable.getPageSize())
					.fetch();
		} else if (startDate != null && endDate != null) {
//			moneyHst = jpaQueryFactory.selectFrom(mth)
//					.leftJoin(mth.buyHst, buyHst)
//					.fetchJoin()
//					.leftJoin(buyHst.goods, goods)
//					.fetchJoin()
//					.leftJoin(goods.merchant, merchant)
//					.fetchJoin()
//					.where(
//							mth.member.membSn.eq(membSn)
//						.and(
//							mth.payMeanCd.eq(payMeanCd)
//						)
//						.and(Expressions.currentTimestamp().between(
//								startRes, endRes)
//								
//						)
//					)
//					.orderBy(mth.moneyTransferHstSn.desc())
//					.offset(rownum).limit(pageable.getPageSize())
//					.fetch();
		}
		List<MoneyTransferHstDTO> moneyHstDto = moneyHst.stream()
										.map(MoneyTransferHstDTO::toDto).collect(Collectors.toList());
		int count = jpaQueryFactory.selectFrom(mth)
										.where(
												mth.member.membSn
												.eq(membSn)
											.and(
												mth.payMeanCd
												.eq(payMeanCd)
												)
										)
										.fetch().size();
		log.debug("QueryDSL :: count=>{}",count);
		return new PageImpl<>(moneyHstDto, pageable, count);
	}

}
