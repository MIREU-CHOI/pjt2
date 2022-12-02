package net.e4net.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.e4net.demo.entity.Goods;
import net.e4net.demo.entity.Merchant;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, String> {
	// 가맹점의 상품 조회
	List<Goods> findByMerchantMerchantSn(Long merchantSn);
	//	List <Goods> findByMerchantSn(Long merchantSn); // id로 Member 찾는 로직
	
	// 가맹점의 상품의 가격 조회
	Goods findByGoodsNo(String goodsNo);
}
