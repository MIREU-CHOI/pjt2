package net.e4net.demo.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.config.SecurityUtil;
import net.e4net.demo.dto.MoneyDTO;
import net.e4net.demo.dto.BuyHstDTO;
import net.e4net.demo.dto.GoodsDTO;
import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.dto.MemberResponseDTO;
import net.e4net.demo.dto.MerchantDTO;
import net.e4net.demo.dto.MoneyTransferHstDTO;
import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.MoneyTransferHst;
import net.e4net.demo.exception.InsufBlce;
import net.e4net.demo.entity.BuyHst;
import net.e4net.demo.entity.Goods;
import net.e4net.demo.entity.Member;
import net.e4net.demo.entity.Merchant;
import net.e4net.demo.repository.BuyHstRepository;
import net.e4net.demo.repository.GoodsRepository;
import net.e4net.demo.repository.MemberRepository;
import net.e4net.demo.repository.MerchantRepository;
import net.e4net.demo.repository.MoneyRepository;
import net.e4net.demo.repository.MoneyTransferRepository;
import net.e4net.demo.repository.QuerydslRepositoryImpl;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW) //REQUIRES_NEW 가 default
@RequiredArgsConstructor
public class MemberService {
	
	private final PasswordEncoder passwordEncoder;
	private final MoneyRepository moneyRepository;
	private final MoneyTransferRepository moneyTransferRepository;
	private final MemberRepository memberRepository;
	private final MerchantRepository merchantRepository;
	private final GoodsRepository goodsRepository;
	private final BuyHstRepository buyHstRepository;
	private final QuerydslRepositoryImpl querydslRepositoryImpl;
	
	private final ModelMapper modelMapper;
	
	// ============ 전체 회원 조회 =============
	public List<Member> findMembers() {
		log.info("MemberService Layer :: Call findMembers Method!");
		return memberRepository.findAll();
	}
	
	// ============= 머니 충전 =============
	public MoneyTransferHst insertMoneyTrans(MoneyTransferHstDTO dto) {
		log.info("MemberService :: Call insertMoneyTrans Method!");
		Long membSn = dto.getMember().getMembSn();
		Member member = Member.builder()
							  .membSn(membSn)
							  .build();
		dto.setMember(member);
	
		MoneyTransferHst entity = modelMapper.map(dto, MoneyTransferHst.class);
		
		return moneyTransferRepository.save(entity);
	}
	// 머니 충전 시 회원머니 테이블 업데이트 
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Money updateMoney(Long membSn, Long amount){	// 서비스 내의 메서드 안으로 불러서 써도 괜찮다!! 한 트랜잭션 처리
		log.info("MemberService :: Call updateMoney Method!");
		Money money = moneyRepository.findByMoneySn(membSn);
		Long balance = money.getMoneyBlce(); // 기존 잔고
		log.debug("기존 잔고 => {}", balance);
		Member member = Member.builder()
							  .membSn(membSn)
							  .build();
		MoneyDTO moneyDto = MoneyDTO.builder()
									.moneyBlce(balance+amount) // 기존 잔고 + 충전액
									.moneySn(membSn)
									.member(member).build();
		Money money2 = modelMapper.map(moneyDto, Money.class);
		return moneyRepository.save(money2);
	}
	
	// ============= 회원 머니 조회 =============
	// * 서비스는 컨트롤러에서 dto 를 받아오고 entity 변환해서 디비 처리한 후
	//   처리한 결과에 따라 다시 dto로 변환해서 컨트롤러에게 dto로 주면 된다!
	public MoneyDTO selectMoney(Long moneySn) {
		log.info("MemberService :: selectMoney sn=>{}",moneySn);
		Money money = moneyRepository.findByMoneySn(moneySn);
//		System.out.println("MoneyBlce => "+ money.getMoneyBlce());
//		modelMapper.getConfiguration().setAmbiguityIgnored(true);	// 이건 setter를 건너뛰는데
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 이건 가장 일치하는 값을 찾도록 설정! 연결 타이트하게? 
		MoneyDTO dto = modelMapper.map(money, MoneyDTO.class);
		return dto;
	}
	
	// ============= 가맹점 조회 =============
	public List<MerchantDTO> getAllMerchants(){
		log.info("MemberService :: getAllMerchants");
		Type listType = new TypeToken<List<MerchantDTO>>(){}.getType();
		List<Merchant> mercList = merchantRepository.findAll();
		List<MerchantDTO> dtoList = modelMapper.map(mercList, listType);
//		System.out.println(dtoList);
		return dtoList;
	}
	// 가맹점의 상품 조회
	public List<GoodsDTO> getMercGoods(Long merchantSn){
		log.info("MemberService :: getMercGoods sn=>{}",merchantSn);
		Type listType = new TypeToken<List<GoodsDTO>>(){}.getType();
//		List<Goods> enList = goodsRepository.findAll();
		List<Goods> goods = goodsRepository.findByMerchantMerchantSn(merchantSn);
//		Goods goods = goodsRepository.findById(goodsNo).get();
//		GoodsDTO dto = modelMapper.map(goods, GoodsDTO.class);
//		System.out.println(goods.get().getGoodsNm());
//		List<Goods> goodList = goodsRepository.findByMerchantMerchantSn(goodsNo);
		List<GoodsDTO> dtoList = modelMapper.map(goods, listType);
//		List<GoodsDTO> dtoList = modelMapper.map(goods.getClass(), listType);
		return dtoList;
	}
	// 가맹점의 상품의 가격 조회 
	public GoodsDTO getGoodsAmt(String goodsNo) {
		log.info("MemberService :: getGoodsAmt sn=>{}",goodsNo);
		Goods goods = goodsRepository.findByGoodsNo(goodsNo);
//		System.out.println("MoneyBlce => "+ money.getMoneyBlce());
//		modelMapper.getConfiguration().setAmbiguityIgnored(true);	// 이건 setter를 건너뛰는데
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 이건 가장 일치하는 값을 찾도록 설정! 연결 타이트하게? 
		GoodsDTO dto = modelMapper.map(goods, GoodsDTO.class);
		return dto;
	}
	
	// ===== (1) 머니 결제 : 구매이력/머니거래이력/회원머니 =====
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public MoneyTransferHst payMoney(BuyHstDTO dto){
		log.debug("MemberService :: Call payMoney Method!");
		Long membSn = dto.getMember().getMembSn();
		Money money = moneyRepository.findById(membSn).orElse(null);
		Long amt = dto.getGoodsAmt() * 1; // 원랜 구매수량까지 받아서 dto.getBuyQtt() 를 곱해야 하는데 일단 이렇게! 
		Long res = money.getMoneyBlce() - amt;
		String goodsNo = dto.getGoods().getGoodsNo();
		log.debug("회원번호{}, 잔고{}, 상품번호{}, 결제 후 금액{}", membSn, money.getMoneyBlce(), goodsNo, res);
		if(res < 0) {
			throw new InsufBlce("잔고에 돈이 부족합니다.");
		}
		// 1) 구매이력 insert buyHst -------------------
		BuyHst buyHst = modelMapper.map(dto, BuyHst.class);
		buyHstRepository.save(buyHst);
		// 2) 머니거래이력 insert moneyTransferHst ----- builder 자체가 영속성으로 인해 알아서 save 해준다 ?
		MoneyTransferHst mth = MoneyTransferHst.builder()
							.buyHst(buyHst)
							.member(Member.builder().membSn(membSn).build())
							.transferTyCd("02")
							.transferAmt(amt)
							.payMeanCd("03")
							.build();
		// 3) 회원머니 update membMoney ----- 변경을 감지해서 값 수정해줌 (알아서 save(update) 해주는 느낌 ?)
		money.setMoneyBlce(res);
		return mth;
	}
	
	// ============= (2) 카드(카카오페이) 결제 : 머니거래이력/회원머니 =============
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BuyHstDTO payCard(BuyHstDTO dto) {
		log.debug("MemberService :: Call payMoney Method!");
		Long membSn = dto.getMember().getMembSn();
		Money money = moneyRepository.findById(membSn).orElse(null);
		Long amt = dto.getGoodsAmt() * 1; // 원랜 구매수량까지 받아서 dto.getBuyQtt() 를 곱해야 하는데 일단 이렇게! 
		Long res = money.getMoneyBlce() - amt;
		String goodsNo = dto.getGoods().getGoodsNo();
		log.debug("회원번호{}, 잔고{}, 상품번호{}, 결제 후 금액{}", membSn, money.getMoneyBlce(), goodsNo, res);
		if(res < 0) {
			throw new InsufBlce("잔고에 돈이 부족합니다.");
		}
		// 1) 구매이력 insert buyHst -------------------
		BuyHst buyHst = modelMapper.map(dto, BuyHst.class);
		buyHstRepository.save(buyHst);
		// 2) 머니거래이력 insert moneyTransferHst ----- builder 자체가 영속성으로 인해 알아서 save 해준다 ?
//		MoneyTransferHst mth = MoneyTransferHst.builder()
//							.buyHst(buyHst)
//							.member(Member.builder().membSn(membSn).build())
//							.transferTyCd("02")
//							.transferAmt(amt)
//							.payMeanCd("03")
//							.build();
		// 3) 회원머니 update membMoney ----- 변경을 감지해서 값 수정해줌 (알아서 save(update) 해주는 느낌 ?)
		money.setMoneyBlce(res);
		return dto;
	}
	// * 거래내역 페이지 (4) 리팩토링 + 페이지네이션 
	public Page<MoneyTransferHstDTO> getMembMoneyTransferHst(
			Pageable pageable, Long membSn, int rownum){
		Page<MoneyTransferHstDTO> res = querydslRepositoryImpl.getAllMoneyHst(pageable, membSn, rownum);
		return res;
	}
	// * 거래내역 페이지 - 결제수단 조회 기능 
	public Page<MoneyTransferHstDTO> getAllMoneyHstByPayMeanCd(
			Pageable pageable, Long membSn, int rownum, String payMeanCd){
		Page<MoneyTransferHstDTO> res = querydslRepositoryImpl
				.getAllMoneyHstByPayMeanCd(pageable, membSn, rownum, payMeanCd);
		return res;
	}
	
	// 거래내역 페이지 (3) 리팩토링 + 페이지네이션 - 수정하다가...
//	public Page<MoneyTransferHstDTO> getMembMoneyTransferHst(
//			Long membSn, int rownum, Pageable pageable){
////			Long membSn = member.getMembSn();
//		log.debug("MemberService :: 거래내역 가져오자 sn=>{}",membSn);
////		Pageable pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by("moneyTransferHstSn").descending());
//		List<MoneyTransferHst> hst = moneyTransferRepository.findAllByMemberMembSn(membSn);
//		List<MoneyTransferHstDTO> hstDto = new ArrayList<>();
//		for (MoneyTransferHst e : hst) {
//			hstDto.add(MoneyTransferHstDTO.toDto(e));
//		}
//		Page<MoneyTransferHstDTO> list = new PageImpl<>(hstDto, pageable, rownum);
////				moneyTransferRepository.findAll(pageable);
//		log.debug("list size => {}", list.getSize());
//		return list;
//	}
	// 거래내역 페이지 (2) 리팩토링  
//	public List<MoneyTransferHstDTO> getMembMoneyTransferHst(Long membSn){
////		Long membSn = member.getMembSn();
//		log.debug("MemberService :: 거래내역 가져오자 sn=>{}",membSn);
//		
//		List<MoneyTransferHst> hst = moneyTransferRepository.findAllByMemberMembSn(membSn);
//		List<MoneyTransferHstDTO> hstDto = new ArrayList<>();
//		for (MoneyTransferHst e : hst) {
//			hstDto.add(MoneyTransferHstDTO.toDto(e));
//		}
//		return hstDto;
//	}
	// 거래내역 페이지
//	public List<MoneyTransferHstDTO> getMembMoneyTransferHst(Long membSn){
////		Long membSn = member.getMembSn();
//		log.debug("MemberService :: 거래내역 가져오자 sn=>{}",membSn);
//		Type listType = new TypeToken<List<MoneyTransferHstDTO>>(){}.getType();
//		List<MoneyTransferHst> mth = 
//				querydslRepositoryImpl.findByMoneyTransferHst(membSn);
//		List<MoneyTransferHstDTO> dtoList = modelMapper.map(mth, listType);
//		return dtoList;
//	}
	
	
	
//	public List<MemberDTO> getAllMembers(){
//	return memberRepository.getAllMembers();
//}
	
	
	
	
	
	
//	public Member fineOne(Long membId) {
//		return memberRepository.findOne(membId);
//	}
	// ====================== 아래는 사용 안함 ==================================
	// (사용x) AuthService 로 회원가입 & 로그인 구현함!!! 
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
	// (사용x) 회원가입 시 아이디 중복확인 - 미르 리팩토링 : 스프링 내에서만 중복확인 한 것 
	private boolean validateDuplicateMember(Member member) {
		Optional<Member> findMembers =
				memberRepository.findByMembId(member.getMembId());
		if (findMembers.isEmpty()) {
			return true; 
		} else {
//				throw new IllegalStateException("이미 존재하는 회원입니다.");
			return false;
		}
	} 
	
	// (사용x) 회원가입 시 아이디 중복 체크 - 리액트에서 버튼 눌렀을 때 요청되는 메서드 V
	public boolean checkMembIdDuplicate(String membId) {
		log.info("MemberService Layer :: Call checkMembIdDuplicate Method!");
		boolean res = memberRepository.existsByMembId(membId);
		return res;
	}
	
	// (사용x) ------- 221111 security login & join -----------------------
    // 헤더에 있는 token값을 토대로 Member의 data를 건내주는 메소드
	public MemberDTO getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberDTO::toDto)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

	// 우편번호 변경 
    @Transactional
    public MemberDTO changeMemberZipCd(String membId, String zipCd) {
        Member member = memberRepository.findByMembId(membId).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        member.setZipCd(zipCd);
        return MemberDTO.toDto(memberRepository.save(member));
    }

    // 패스워드 변경 - token값을 토대로 찾아낸 member를 찾아낸 다음 제시된 예전 패스워드와 DB를 비교
    @Transactional
    public MemberDTO changeMembPwd(String membId, String exPassword, String newPassword) {
        Member member = memberRepository.findById(
        		SecurityUtil.getCurrentMemberId())
        		.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if (!passwordEncoder.matches(exPassword, member.getMembPwd())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        member.setMembPwd(passwordEncoder.encode((newPassword))); // 맞으면 새 패스워드로 교체
        return MemberDTO.toDto(memberRepository.save(member));
    }
	// --------------------------------------------------------------------


    
    
    
	
//	private void validateDuplicateMember(Member member) {
//		Optional<Member> findMembers =
//				memberRepository.findByMembId(member.getMembId());
//		if (!findMembers.isEmpty()) {
//			throw new IllegalStateException("이미 존재하는 회원입니다.");
//		}
//	} 
	
	// ================= 221108 ===============================
//	public boolean login(Member member) {
//		Member findUser = memberRepository.findByMembId(member.getMembId());
//        if(findUser == null){
//            return false;
//        }
//        if(!findUser.getMembPwd().equals(member.getMembPwd())){
//            return false;
//        }
//        return true;
//    }
	
//	public String login(String membId, String password) {
//		Optional<Member> member = memberRepository.findByMembId(membId);
//		log.info("db password = {}, input password = {}", member.get().getMembPwd(), password);
//		if(member.get().getMembPwd().equals(password)) {
//			return "Success";
//		}
//		return "Failed";
//	}
//	
//	public String signup(Member request){
//		memberRepository.save(Member.builder()
//                .membId(request.get())
//                .password(request.getPassword())
//                .userName(request.getUserName())
//                .build());
//        return "Success";
//    }
	// ======================================================
	
	
	
//	public MemberDTO insertMember(MemberDTO member) {
//		return memberRepository.insertMember(member);
//	}
//	
//	public List<MemberDTO> getAllMembers(){
//		return memberRepository.getAllMembers();
//	}
//	
//	public MemberDTO getMemberByMemId(String membId) {
//		return memberRepository.getMemberByMemId(membId);
//	}
//	
//	public void updateMembPwd(String membId, MemberDTO member) {
//		memberRepository.updateMembPwd(membId, member);
//	}
//	
//	public void deleteMember(String membId) {
//		memberRepository.deleteMember(membId);
//	}
	
	
	/////////////// 교육 실습 //////////////////////
//	@Transactional
//	public void join(Member member) {
//		MembMoney.createMembMoney(memberRepository.save(Member.createMember("Role_admin")));
//	}
	
//	@Transactional
//	public Member join(Member member) {
//		validatedup
//	}
//	
//	private void validateDuplicateMember(Member member) {
//		List<Member> findMembers = memberRepository.findBy
//	}

}
