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
import net.e4net.demo.entity.Goods;
import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.Member;
import net.e4net.demo.repository.GoodsRepository;
import net.e4net.demo.repository.MemberRepository;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GoodsService {
	
	private final GoodsRepository goodsRepository;
	private final MemberRepository memberRepository; // 지우자
	private final ModelMapper modelMapper;
	
	// 상품 등록
	public String saveGoods(GoodsDTO goodsDto) {
		Goods goods = modelMapper.map(goodsDto, Goods.class);
		goodsRepository.save(goods);
		return goods.getGoodsNo();
	}
	
	// 상품 목록 조회 
	public List<Goods> findGoods(){
		return goodsRepository.findAll();
	}
	
	// 상품 수정...? 이게 맞는지 모르겠네 !!!!!
	public Optional<Goods> findOne(String goodsNo) {
		return goodsRepository.findById(goodsNo);
	}
	
	
	
	
	
	
	// ======================================== MemberRepository 참고용 
	// 회원가입
	public Long join(MemberDTO dto) {
		log.info("MemberService Layer :: Call join Method!");
		Member member = modelMapper.map(dto, Member.class);
		boolean res = validateDuplicateMember(member); // 중복 회원 검증
		if (res) {
			memberRepository.save(member);			
		} 
		return member.getMembSn();
	}
	
	// 전체 회원 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
//	public Member fineOne(Long membId) {
//		return memberRepository.findOne(membId);
//	}
	
	// 회원가입 시 아이디 중복 체크 - 리액트에서 버튼 눌렀을 때 요청되는 메서드 V
	public boolean checkMembIdDuplicate(String membId) {
		log.info("Auth Service's Service Layer :: Call checkMembIdDuplicate Method!");
		boolean res = memberRepository.existsByMembId(membId);
		return res;
	}
	
	
	
	
	// ---------------------------------
	// 회원가입 시 아이디 중복확인 - 미르 리팩토링 : 스프링 내에서만 중복확인 한 것 
	// --- 위 중복확인 메서드를 사용함
	private boolean validateDuplicateMember(Member member) {
		Optional<Member> findMembers =
				memberRepository.findByMembId(member.getMembId());
		if (findMembers.isEmpty()) {
			return true; 
		} else {
//			throw new IllegalStateException("이미 존재하는 회원입니다.");
			return false;
		}
	} 


}
