-- [ 회원 ] tb_memb 전체 조회
SELECT memb_sn, memb_id, memb_nm, memb_pwd, memb_cls, 
	frst_regist_dt, frst_regist_memb_sn, last_change_dt, last_regist_memb_sn, use_yn, detail_addr,
	email_addr, last_login_dtm,  memb_status_cd, mobile_no, zip_addr, zip_cd
FROM public.tb_memb order by 1 desc;

-- [ 회원 머니 ] tb_memb_money 전체 조회
SELECT money_sn, money_blce, last_change_dt, memb_sn, frst_regist_dt, frst_regist_memb_sn, last_regist_memb_sn, use_yn
FROM public.tb_memb_money order by 1 desc;

update tb_memb_money set money_blce = 500 where money_sn = 1;

-- [ 머니 거래 이력 ] tb_money_transfer_hst 전체 조회 
SELECT money_transfer_hst_sn, transfer_amt, memb_sn, buy_hst_sn, pay_transfer_no, transfer_ty_cd,
	frst_regist_dt, frst_regist_memb_sn, last_change_dt, last_regist_memb_sn, use_yn, 
	pay_mean_cd
FROM public.tb_money_transfer_hst order by 1 desc;

--update tb_money_transfer_hst set buy_hst_sn = 0 where money_transfer_hst_sn = 1;

-- [ 구매이력 ] tb_buy_hst 전체 조회 
SELECT buy_hst_sn, goods_no, memb_sn, goods_amt, frst_regist_dt, frst_regist_memb_sn, last_change_dt
	, buy_amt, buy_qtt, last_regist_memb_sn, use_yn
FROM public.tb_buy_hst order by 1 desc;

-- [ 가맹점 ] tb_merchant 전체 조회 
SELECT merchant_sn, memb_sn, merchant_nm, tel_no, zip_addr, detail_addr, frst_regist_dt, frst_regist_memb_sn, 
	last_change_dt, last_regist_memb_sn, use_yn, email_addr, merchant_desc, merchant_url, zip_cd
FROM public.tb_merchant;


select * from tb_money_transfer_hst tmth, tb_buy_hst tbh where tbh.buy_hst_sn = tmth.buy_hst_sn ;

-- [ 로그인이력 ] tb_memb_login_hst 전체 조회 
SELECT login_sn, frst_regist_dt, frst_regist_memb_sn, last_change_dt, last_regist_memb_sn, use_yn, connect_ip, memb_sn
FROM public.tb_memb_login_hst;


--update tb_money_transfer_hst set transfer_ty_cd = '01' where transfer_ty_cd = '1';

-- 가맹점 더미 데이터
--INSERT INTO public.tb_merchant (merchant_sn, memb_sn, merchant_nm, tel_no, zip_addr, detail_addr, frst_regist_dt )
--	VALUES(nextval('merchant_seq'), 2, '이랜드', '0212341234', '서울시 금천구','123', now()); -- sn 2
--INSERT INTO public.tb_merchant (merchant_sn, memb_sn, merchant_nm, tel_no, zip_addr, detail_addr, frst_regist_dt )
--	VALUES(nextval('merchant_seq'), 2, '삼성', '0256785678', '서울시 강남구','789', now()); -- sn 3
--INSERT INTO public.tb_merchant (merchant_sn, memb_sn, merchant_nm, tel_no, zip_addr, detail_addr, frst_regist_dt )
--	VALUES(nextval('merchant_seq'), 2, '유니클로', '0255667788', '서울시 구로구','777', now()); -- sn 5
-- memb_sn 2, 3, 7번 친구가 판매자! 

-- [ 상품 ] tb_goods 전체 조회 
SELECT GOODS_NO, MERCHANT_SN, GOODS_NM, GOODS_AMT, GOODS_QTT, GOODS_SELL_QTT, GOODS_CLS_DT, 
	GOODS_MODEL_NO, GOODS_SHPP_COST, REAL_FILE_NM, GOODS_IMG_PATH, GOODS_DESC 
FROM public.tb_goods order by 1 desc;

select * from tb_goods where MERCHANT_SN = 2 order by 1 desc;

-- 상품 더미 데이터
-- * sn2 이랜드 가맹점  
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 2, '청바지1', 50000, 100, now());
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 2, '자켓1', 70000, 150, now());
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 2, '양말1', 3000, 50, now());
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 2, '티셔츠1', 15000, 200, now());
-- * sn3 삼성 가맹점
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 3, '갤럭시S', 1000000, 200, now());
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 3, '갤럭시노트22', 1300000, 200, now());
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 3, '갤럭시Z플립', 1500000, 250, now());
-- * sn5 유니클로 가맹점
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 4, '후리스', 30000, 500, now());
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 4, '스웨터1', 50000, 220, now());
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 4, '바지1', 40000, 200, now());
INSERT INTO public.tb_goods (goods_no, merchant_sn, goods_nm, goods_amt, goods_qtt, frst_regist_dt)
	VALUES(nextval('goods_seq'), 4, '양말1', 10, 500, now());




update tb_memb set memb_cls = 'ROLE_SELLER' where memb_sn =2;
--update public.tb_memb_money set money_blce = 0 where money_sn =1;
select count(memb_id) from tb_memb where memb_id = 'aa';
--delete from public.tb_memb_money;
select sum(p.transfer_amt) from tb_money_transfer_hst p where p.memb_sn = ?1;
delete from public.tb_merchant where merchant_sn = 6;



