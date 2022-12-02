package net.e4net.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.Member;

@Repository
public interface MoneyRepository extends JpaRepository<Money, Long>{

//	public Money findMoneyByMoneySn(Long MoneySn); //selectOne
	Money findByMoneySn(Long MoneySn); // 회원 한명 기존 머니잔고 조회; select * from tb_memb_money where money_sn = money_sn;
}
