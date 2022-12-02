package net.e4net.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.MoneyTransferHst;
import net.e4net.demo.dto.MoneyTransferHstDTO;
import net.e4net.demo.entity.Member;

@Repository
public interface MoneyTransferRepository 
	extends JpaRepository<MoneyTransferHst, Long>{

	// 거래내역 
	List<MoneyTransferHst> findAllByMemberMembSn(Long membSn);
	
//	Page<MoneyTransferHst> findAllByMemberMembSn(Long membSn);

	// 총 충전 금액 쿼리
//    @Transactional
//    @Query("select sum(p.transfer_amt) from tb_money_transfer_hst p where p.memb_sn = ?1")
//    Long amountSum(Member member);
}
