package net.e4net.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.e4net.demo.dto.MoneyTransferHstDTO;
import net.e4net.demo.entity.Member;
import net.e4net.demo.entity.MoneyTransferHst;

public interface QuerydslRepositoryCustom {

	List<MoneyTransferHst> findByMoneyTransferHst(Long membSn);
	
	Page<MoneyTransferHstDTO> getAllMoneyHst(Pageable pageable, Long membSn, int rownum);
}
