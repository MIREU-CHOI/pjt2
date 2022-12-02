package net.e4net.demo.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.el.parser.AstFalse;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TB_MEMB_MONEY")
@SequenceGenerator(
		name = "MONEY_SEQ_GENERATOR",
		sequenceName = "MONEY_SEQ", // 매핑할 데이터베이스 시퀀스 이름
		initialValue = 1,
		allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money extends CommonData{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MONEY_SEQ_GENERATOR")
	@Column(name = "MONEY_SN", length = 10)
	private Long moneySn;	// 회원머니 번호
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.PERSIST)   // join 할 때 서로를 참조해야 하니까
//	@JoinColumn  :  외래키를 정의하는 어노테이션 
	@JoinColumn(name = "MEMB_SN", referencedColumnName = "MEMB_SN")
	private Member member;	// 회원번호
	
	@Column(name = "MONEY_BLCE", length = 15)
	private Long moneyBlce;	// 머니잔고 

	public void setMoneyBlce(Long moneyBlce) {
		this.moneyBlce = moneyBlce;
	}
	
	
	
	// 미르 
	@Builder
	private Money(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
			Timestamp lastRegistDt, Long moneySn, Member member, Long moneyBlce) {
		super(useYn, frstRegistMembSn, frstRegistDt, lastRegistMembSn, lastRegistDt);
		this.moneySn = moneySn;
		this.member = member;
		this.moneyBlce = moneyBlce;
		this.member.setMembMoney(this);
	}
	
	// default 값 넣어주기 
	@PrePersist
	public void setting() {
		this.moneyBlce = 0l;
	}
	
	// 무조건 이 빌드패턴을 이용해야만 하도록 함 
	public static Money createMembMoney(Member member) {
		return Money.builder()
				.member(member)
				.build();
	}

	
	// 교육 
//	@Builder
//	private MembMoney(Member member) {
//		this.member = member; 
//		this.moneyBlce = moneyBlce;
//		this.member.setMembMoney(this);
//	}
	
	
	
	



}
