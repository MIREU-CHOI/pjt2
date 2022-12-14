package net.e4net.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.dto.BuyHstDTO;
import net.e4net.demo.dto.ChangePasswordRequestDTO;
import net.e4net.demo.dto.GoodsDTO;
import net.e4net.demo.dto.MailDTO;
import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.dto.MemberRequestDTO;
import net.e4net.demo.dto.MerchantDTO;
import net.e4net.demo.dto.MoneyDTO;
import net.e4net.demo.dto.MoneyTransferHstDTO;
import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.entity.Member;
import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.MoneyTransferHst;
import net.e4net.demo.repository.QuerydslRepositoryImpl;
import net.e4net.demo.service.CertificationService;
import net.e4net.demo.service.MemberService;
import net.e4net.demo.service.SendEmailService;
import retrofit2.http.GET;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final CertificationService certificationService;
	private final QuerydslRepositoryImpl querydslRepositoryImpl;
	private final SendEmailService sendEmailService;
	
	private static final int offset = 5;
	
	// =========== ???????????? ?????? ================= https://1-7171771.tistory.com/85
	// membId??? emailAddr??? ??????????????? check?????? ????????????
	@GetMapping("/member/findPwd/{userId}/{userEmail}")
    public @ResponseBody Map<String, Boolean> findPwd(
//    					String membId, String emailAddr
    				@PathVariable("userId") String userId
    				, @PathVariable("userEmail") String userEmail){
		log.debug("MemberController :: \n	?????? ??????^~^ userId => {} , userEmail => {}", userId, userEmail);
        Map<String,Boolean> json = new HashMap<>();
        boolean pwFindCheck = memberService.findPwd(userId, userEmail);
        log.debug("\n	pwFindCheck => {}", pwFindCheck);
        json.put("check", pwFindCheck);
        return json;
    }
	// ????????? ???????????? ????????????????????? ???????????? ????????? ????????????????????? ???????????? pw??? ???????????? ????????????
    @PostMapping("/member/findPwd/sendEmail")
    public void sendEmail(@RequestBody MemberDTO dto){
    	String membId = dto.getMembId();
    	String emailAddr = dto.getEmailAddr();
		log.debug("MemberController :: \n	?????? ?????????^o^! membId => {} , emailAdder => {}", membId, emailAddr);
    	MailDTO mailDto = sendEmailService.createMailAndChangePassword(dto);
        sendEmailService.mailSend(mailDto);
    }
    // ?????????????????? - ????????? ???????????? ????????????????????? ???????????? ????????? ????????????????????? ???????????? pw??? ???????????? ????????????
    @PostMapping("/android/findPwd/sendEmail")
    public void androidSendEmail( MemberDTO dto){
    	String membId = dto.getMembId();
    	String emailAddr = dto.getEmailAddr();
		log.debug("androidSendEmail controll :: \n	?????? ?????????^o^! membId => {} , emailAdder => {}", membId, emailAddr);
    	MailDTO mailDto = sendEmailService.createMailAndChangePassword(dto);
        sendEmailService.mailSend(mailDto);
    }
	
	
	// =========== ???????????? ??? ????????? ?????? ?????? =================
	@GetMapping("/member/exists/{membId}")
	public ResponseEntity<Boolean> checkMembIdDuplicate(@PathVariable("membId") String membId) {
		System.out.println("membId => " + membId);
		log.debug("MemberController Layer :: Call checkMembIdDuplicate Method!");
		if (memberService.checkMembIdDuplicate(membId)) {
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
		return ResponseEntity.status(HttpStatus.OK).body(true);
//		return ResponseEntity.ok(memberService.checkMembIdDuplicate(membId));
	}
	// ??????????????? ??? ????????? ?????? ?????? 
	@GetMapping("/android/member/exists/{membId}")
	public ResponseEntity<Map<String, Boolean>> androidCheckMembIdDuplicate(@PathVariable("membId") String membId) {
		System.out.println("membId => " + membId);
		log.debug("MemberController Layer :: Call checkMembIdDuplicate Method!");
		Map<String, Boolean> map = new HashMap<String, Boolean>();  
		if (memberService.checkMembIdDuplicate(membId)) {
			map.put("res", false);
//			ResponseEntity<Boolean> res = ResponseEntity.status(HttpStatus.OK).body(false);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}else {
			map.put("res", true);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}
//		return ResponseEntity.ok(memberService.checkMembIdDuplicate(membId));
	}
	// =========== ??????????????? ?????? coolSMS 221110 =================
	@GetMapping("/check/sendSMS/{mobileNo}")
	public ResponseEntity<String> sendSMS(@PathVariable("mobileNo") String mobileNo) {
//        String cerNum = certificationService.certifiedPhoneNumber(mobileNo);
        String cerNum = "7963"; 	// ????????? ??? 
		log.debug("????????? ?????? : {} ???????????? : {}", mobileNo, cerNum);
		return ResponseEntity.status(HttpStatus.OK).body(cerNum);
	}
	
	// ============= ?????? ?????? 221215 =============
	@GetMapping("/member/{membSn}")
	public ResponseEntity<MemberDTO> selectMember(@PathVariable("membSn") Long membSn){
//			log.debug("")
		log.debug("\nMemberController :: selectMember\n	membSn => {}",membSn);
		MemberDTO dto = memberService.selectMember(membSn);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	// ============= ?????? pwd ?????? 221215 =============
	@PostMapping("/member/update")
    public ResponseEntity<MemberDTO> updateMembPwd(@RequestBody MemberDTO memberDTO) {
		log.debug("\nMemberController :: updateMembPwd!\n	getMembPwd => {}",memberDTO.getMembPwd());
        return ResponseEntity.ok(memberService.updateMembPwd(memberDTO));
    }
	// =========== ?????? ?????? 221115 ===========
	@PostMapping("/member/charge")
	public ResponseEntity<MoneyTransferHstDTO> chargeMoney(@RequestBody MoneyTransferHstDTO transDto) {
		Long membSn = transDto.getMember().getMembSn();
		log.debug("MemberController :: chargeMoney membSn:{}",membSn);
		Long amount = transDto.getTransferAmt();
		MoneyTransferHst mth = memberService.insertMoneyTrans(transDto);
		log.debug("???????????????????????? => {},  ?????? => {}", mth.getMoneyTransferHstSn(), amount);
		memberService.updateMoney(membSn, amount);
		return ResponseEntity.status(HttpStatus.OK).body(transDto);
	}
	// ============= ?????? ?????? ?????? =============
	@GetMapping("/member/money/{membSn}")
	public ResponseEntity<MoneyDTO> selectMoney(@PathVariable("membSn") Long membSn){
//		log.debug("")
		log.debug("MemberController :: selectMoney membSn:{}",membSn);
		MoneyDTO dto = memberService.selectMoney(membSn);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	// ============= ????????? ?????? =============
	@GetMapping("/member/merchants")
	public ResponseEntity<List<MerchantDTO>> getAllMerchants(){
		log.debug("MemberController :: getAllMerchants");
		List<MerchantDTO> dtoList = memberService.getAllMerchants();
		return ResponseEntity.status(HttpStatus.OK).body(dtoList);
	}
	// ???????????? ?????? ?????? 
	@GetMapping("/member/merchants/{merchantSn}")
	public ResponseEntity<List<GoodsDTO>> getMercGoods(@PathVariable("merchantSn") Long merchantSn){
		log.debug("MemberController :: getMercGoods sn=>{}", merchantSn);
		List<GoodsDTO> dtoList = memberService.getMercGoods(merchantSn);
		return ResponseEntity.status(HttpStatus.OK).body(dtoList);
	}
	// ???????????? ????????? ?????? ?????? 
	@GetMapping("/member/goods/{goodsNo}")
	public ResponseEntity<GoodsDTO> getGoodsAmt(@PathVariable("goodsNo") String goodsNo){
		log.debug("MemberController :: getMercGoods sn=>{}", goodsNo);
		GoodsDTO dtoList = memberService.getGoodsAmt(goodsNo);
		return ResponseEntity.status(HttpStatus.OK).body(dtoList);
	}
	
	// =========== (1) "????????????"??? ?????? 221121 ===========
	@PostMapping("/member/payMoney")
	public ResponseEntity<MoneyTransferHst> payMoney(@RequestBody BuyHstDTO buyHstDto) {
		Long membSn = buyHstDto.getMember().getMembSn(); 
		log.debug("MemberController :: payMoney membSn:{}",membSn);
//		System.out.println("BuyAmt => "+buyHstDto.getBuyAmt());
		MoneyTransferHst mth = memberService.payMoney(buyHstDto);
		return ResponseEntity.status(HttpStatus.OK).body(mth); // ?????????????????? ???????????? ??????! 
	}
	
	// =========== (2) "??????(???????????????)"??? ?????? 221121 ===========
	@PostMapping("/member/payCard")
	public ResponseEntity<BuyHstDTO> payCard(@RequestBody BuyHstDTO buyHstDto) {
		Long membSn = buyHstDto.getMember().getMembSn(); 
		log.debug("MemberController :: payCard membSn:{}",membSn);
//		System.out.println("BuyAmt => "+buyHstDto.getBuyAmt());
		BuyHstDTO dto = memberService.payCard(buyHstDto);
		return ResponseEntity.status(HttpStatus.OK).body(dto); // ?????????????????? ???????????? ??????! 
	}
	
	// =========== ** ?????????????????? ????????? =========== 
	@PostMapping("/member/moneyTransferHst/{membSn}/{rownum}")
	public ResponseEntity<Page<MoneyTransferHstDTO>> getMembMoneyTransferHst(
			@PathVariable("membSn") Long membSn, 
			@PathVariable("rownum") int rownum,
			@PageableDefault(page = 1, size = 10) Pageable page){
		log.debug("MemberController :: ?????? ???????????? sn=> {}",membSn);
		Page<MoneyTransferHstDTO> list = memberService.getAllMembMoneyTransferHst(page,membSn,rownum);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	// =========== ** ?????????????????? ????????? - ???????????? ?????? payMeanCd & ?????? ?????? startDate ~ endDate 
	@PostMapping("/member/moneyTransferHstByPayMean/{membSn}/{rownum}")
	public ResponseEntity<Page<MoneyTransferHstDTO>> getMembMoneyTransferHst(
			@PathVariable("membSn") Long membSn
			, @PathVariable("rownum") int rownum
			, @PageableDefault(page = 1, size = 10) Pageable page
			, @RequestParam String payMeanCd
			, @RequestParam String startDate
			, @RequestParam String endDate){
		log.debug("MemberController :: ?????? ???????????? \n	???????????? => {}, row => {} ???????????? => {} "
				+ "\n	?????? {} ~ {}",membSn, rownum, payMeanCd, startDate, endDate);
//		String syear = startDate.substring(0,4);
//		String smonth = startDate.substring(4,6);
//		String sday = startDate.substring(6);
//		String startRes = syear+"-"+smonth+"-"+sday;
//		String eyear = endDate.substring(0,4);
//		String emonth = endDate.substring(4,6);
//		String eday = endDate.substring(6);
//		String endRes = eyear+"-"+emonth+"-"+eday;
		
		Page<MoneyTransferHstDTO> list = memberService.getMoneyHstByPayMeanCd(page,membSn,rownum, payMeanCd, startDate, endDate);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
//	@GetMapping("/member/moneyTransferHst/{membSn}")
//	public ResponseEntity<List<MoneyTransferHstDTO>> getMembMoneyTransferHst(
//					@PathVariable("membSn") Long membSn){
//		log.info("MemberController :: ???????????? ???????????? sn=>{}",membSn);
//		List<MoneyTransferHstDTO> dtoList = 
//				memberService.getMembMoneyTransferHst(membSn);
//		return ResponseEntity.status(HttpStatus.OK).body(dtoList);
//	}
	
	
	
	
	
	
	
	

	// ====================== ????????? ?????? ?????? ==================================

	// -------------- 221111 security login & join --------------
	// ????????????
	@GetMapping("/member/me")
	public ResponseEntity<MemberDTO> getMyMemberInfo() {
		MemberDTO myInfoBySecurity = memberService.getMyInfoBySecurity();
		System.out.println(myInfoBySecurity.getZipCd());
		return ResponseEntity.ok((myInfoBySecurity));
		// return ResponseEntity.ok(memberService.getMyInfoBySecurity());
	}
	@PostMapping("/member/nickname")
	public ResponseEntity<MemberDTO> setMembZipCd(@RequestBody MemberDTO request) {
		return ResponseEntity.ok(memberService.changeMemberZipCd(request.getMembId(), request.getMembPwd()));
	}
	@PostMapping("/member/password")
	public ResponseEntity<MemberDTO> setMembPwd(@RequestBody ChangePasswordRequestDTO request) {
		return ResponseEntity
				.ok(memberService.changeMembPwd(request.getMembId(), request.getExMembPwd(), request.getNewMembPwd()));
	}

	// * AuthController ??? signup ??? join ???????????? ?????????
	// -------------- 221109 --------------
	@PostMapping("/member/join")
	public ResponseEntity<MemberDTO> join(@RequestBody MemberDTO dto) {
		log.info("MemberController Layer :: Call join Method!");
		System.out.println("MembId => " + dto.getMembId());
		Long membSn = memberService.join(dto);
		System.out.println("???????????? membSn => " + membSn);
		return new ResponseEntity<MemberDTO>(dto, HttpStatus.OK);
	}

	// -------------- 221108 --------------
//	@RequestMapping("/member/insert")
//	public MemberDTO insertMember(@RequestParam String member) {
	// return memberService.insertMember(member);
//		log.info("\n{}(???/???), ??????!", member);
//		System.out.println("!!!!!!!!!!!!!!!!!!!!!");
//		return null;
//	}
//	@PostMapping("/login")
//	public ResponseEntity login(@RequestBody Member request) {
//		log.info("userId = {}, password = {}", request.getMembId(), request.getMembPwd());
//		if(memberService.login(request.getMembId(), request.getMembPwd()).equals("Success")) {
//			return new ResponseEntity(HttpStatus.OK);
//		}
//		return new ResponseEntity(HttpStatus.BAD_REQUEST);
//	}
//	@GetMapping("/member/login")
//    public String login() {
//        return "login";
//    }
//
//    @PostMapping("/member/login")
//    public String loginId(@ModelAttribute Member member) {
//        if(memberService.login(member)){
//            return "redirect:/";
//        }
//        return "login";
//    }
	// ======================================================

//	
//	public List<MemberDTO> getAllMembers(){
//		return memberService.getAllMembers();
//	}
//	
//	public MemberDTO getMemberByMembId(String membId) {
//		return memberService.getMemberByMemId(membId);
//	}
//	
//	public void updateMembPwd(String membId, MemberDTO member) {
//		memberService.updateMembPwd(membId, member);
//	}
//	
//	public void deleteMember(String membId) {
//		memberService.deleteMember(membId);
//	}

	///////////////// ?????? ?????? ///////////////
//	@PostMapping("/member/new")
//	public void add(@RequestBody Member member) {
//		memberService.join(member);
//	}

}
