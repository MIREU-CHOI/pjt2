create table tb_memb (
	memb_sn serial primary key,
	MEMB_CLS varchar(20) not null,
	MEMB_STATUS_CD char(2) not null,
	MEMB_ID varchar(12) not null,
	MEMB_PWD varchar(20) not null,
	MEMB_NM varchar(100) not null,
	MOBILE_NO varchar(20),
	EMAIL_ADDR varchar(100),
	ZIP_CD varchar(6),
	ZIP_ADDR varchar(150),
	DETAIL_ADDR varchar(150),
	LAST_LOGIN_DTM date,
	USE_YN char(1),
	FRST_REGIST_MEMB_SN integer(10),
	FRST_REGIST_DT char(14),
	LAST_REGIST_MEMB_SN int(10),
	LAST_CHANGE_DT char(14)
);

--CREATE TABLE actors (
--	actor_id SERIAL PRIMARY KEY, 
--	first_name VARCHAR(150),
--	last_name VARCHAR(150) NOT NULL,
--	gender CHAR(1),
--	date_of_birth DATE,
--	add_date DATE,
--	update_date Date
--);

-- tb_memb 전체 조회
SELECT memb_sn, frst_regist_dt, frst_regist_memb_sn, last_change_dt, last_regist_memb_sn, use_yn, detail_addr,
	email_addr, last_login_dtm, memb_id, memb_nm, memb_pwd, memb_status_cd, mobile_no, memb_cls , zip_addr, zip_cd
FROM public.tb_memb;

-- tb_memb_money 전체 조회
SELECT money_sn, frst_regist_dt, frst_regist_memb_sn, last_change_dt, last_regist_memb_sn, use_yn, money_blce, memb_sn
FROM public.tb_memb_money;

select count(memb_id) from tb_memb where memb_id = 'aa';

-- tb_money_transfer_hst 전체 조회
SELECT money_transfer_hst_sn, frst_regist_dt, frst_regist_memb_sn, last_change_dt, last_regist_memb_sn, use_yn, 
	pay_mean_cd, pay_transfer_no, transfer_amt, transfer_ty_cd, memb_sn
FROM public.tb_money_transfer_hst;

select sum(p.transfer_amt) from tb_money_transfer_hst p where p.memb_sn = ?1











