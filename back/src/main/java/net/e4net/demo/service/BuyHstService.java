package net.e4net.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.dto.BuyHstDTO;
import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.entity.BuyHst;
import net.e4net.demo.entity.Goods;
import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.Member;
import net.e4net.demo.repository.BuyHstRepository;
import net.e4net.demo.repository.GoodsRepository;
import net.e4net.demo.repository.MemberRepository;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BuyHstService {
	
	private final BuyHstRepository buyHstRepository;
	private final GoodsRepository goodsRepository;
	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;
	
	// 구매이력 조회 
	public List<BuyHst> findBuyHst(){
		return buyHstRepository.findAll();
	}
	
	// =========================================
	// 구매 ... 메소드 필요 있? 없? 
//	public void saveGoods(Goods goods) {
//		goodsRepository.save(goods);
//	}
	public void buy(BuyHstDTO buyHstDTO) {
		BuyHst buyHst = modelMapper.map(buyHstDTO,BuyHst.class);
		buyHstRepository.save(buyHst);
	}

	

}
