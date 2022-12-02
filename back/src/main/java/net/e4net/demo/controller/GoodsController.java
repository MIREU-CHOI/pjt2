package net.e4net.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.dto.GoodsDTO;
import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.entity.Goods;
import net.e4net.demo.service.CertificationService;
import net.e4net.demo.service.GoodsService;
import net.e4net.demo.service.MemberService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class GoodsController {
	
	private final GoodsService goodsService;
	private final MemberService memberService;
	private final CertificationService certificationService;
	
	// 상품 등록 
	@PostMapping(value = "/goods/new")
	public ResponseEntity<GoodsDTO> createGoods(@RequestBody GoodsDTO dto) {
		log.info("GoodsController Layer :: Call createGoods Method!");
		String goodsNo = goodsService.saveGoods(dto);
        System.out.println("상품등록 완료 goodsNo => " + goodsNo);
		return new ResponseEntity<GoodsDTO>(dto, HttpStatus.OK); //   /goods url로 가는 거 만들어야지  
	}
	
	// 상품 등록 폼 으로 가기 ==> 리액트에서 하면 되지 ?
//	@GetMapping(value = "/goods/new")
//	public String createGoodsForm() {
//		return "goods/createGoodsForm";
//	}
	
	// 상품 목록 조회 
	@GetMapping(value = "/goods")
	public List<Goods> goodsList( ) {
		List<Goods> goods = goodsService.findGoods();
		return goods;	// 상품 목록 반환해서 리액트에서 펼치기 ?
	}
	
	/** 상품 수정 **/
	@GetMapping("/goods/{goodsNo}/edit")
	public ResponseEntity<GoodsDTO> updateGoods(@RequestBody GoodsDTO dto) {
		log.info("GoodsController Layer :: Call updateGoods Method!");
		String goodsNo = goodsService.saveGoods(dto);
        System.out.println("상품수정 완료 goodsNo => " + goodsNo);
		return new ResponseEntity<GoodsDTO>(dto, HttpStatus.OK); //   /goods url로 가는 거 만들어야지  
	}
	
	
	// 상품수정 폼 가는거 만들다 말았지
//	@GetMapping(value = "/goods/{goodsNo}/edit")
//	public void 
	
	
	
	
	

}
