package net.e4net.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TB_MEMB_LOGIN_HST")
@SequenceGenerator(
		name = "LOGIN_HST_SEQ_GENERATOR",
		sequenceName = "LOGIN_HST_SEQ",
		initialValue = 1,
		allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MembLoginHst extends CommonData{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGIN_HST_SEQ_GENERATOR")
	@Column(name = "LOGIN_SN")
	private Long loginSn;		// 로그인일련번호 , length = 10
	
//	@ManyToOne : N:1 관계를 표현하는 어노테이션이다. @ManyToOne이 붙은 엔티티가 M이고 반대 엔티티가 1일 때 붙인다.
	@ManyToOne
	@JoinColumn(name = "MEMB_SN", referencedColumnName = "MEMB_SN")
	private Member member;		// 회원번호
	
	@Column(name = "CONNECT_IP")
	private String connectIp;	// 접속IP , length = 15


	@Builder
	private MembLoginHst(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
			Timestamp lastRegistDt, Long loginSn, Member member, String connectIp) {
		super("Y", frstRegistMembSn, frstRegistDt, lastRegistMembSn, lastRegistDt);
		this.loginSn = loginSn;
		this.member = member;
		this.connectIp = connectIp;
	}
	
	public static MembLoginHst createMembLoginHst(Long membSn, String connectIp) {
		return MembLoginHst.builder()
					.member(Member.builder().membSn(membSn).build())
					.connectIp(connectIp)
					.build();
	}
	
	
	
	
	
	
}






