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
import net.e4net.demo.dto.GoodsDTO;
import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.dto.MerchantDTO;
import net.e4net.demo.entity.Goods;
import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.Member;
import net.e4net.demo.entity.Merchant;
import net.e4net.demo.repository.GoodsRepository;
import net.e4net.demo.repository.MemberRepository;
import net.e4net.demo.repository.MerchantRepository;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MerchantService {
	
	private final MerchantRepository merchantRepository;
	private final GoodsRepository goodsRepository;
	private final MemberRepository memberRepository; // 지우자
	private final ModelMapper modelMapper;
	
	// 가맹점 조회 
	public List<Merchant> findMerchant(){
		log.info("MerchantService Layer :: Call findMerchant Method!");
		return merchantRepository.findAll();
	}
	
	
	
	
	
	// ======================================================================
	// 가맹점 등록 - 사전 등록 준비 사항
	public Long saveMerchant(MerchantDTO merchantDto) {
		log.info("MerchantService Layer :: Call saveMerchant Method!");
		Merchant merchant = modelMapper.map(merchantDto, Merchant.class);
		merchantRepository.save(merchant);
		return merchant.getMerchantSn();
	}
	
	// 상품 수정...? 이게 맞는지 모르겠네 !!!!!
	public Optional<Merchant> findOneMerchant(Long merchantSn) {
		log.info("MerchantService Layer :: Call findOneMerchant Method!");
		return merchantRepository.findById(merchantSn);
	}
	
	
	
	
	
	
}
