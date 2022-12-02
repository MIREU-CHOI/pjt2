package net.e4net.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public abstract class CommonData {
	
	@Column(name = "USE_YN")
	private String useYn;			// 사용여부

	@Column(name = "FRST_REGIST_MEMB_SN", length = 10)
	private Long frstRegistMembSn;	// 등록회원번호
	
	@CreatedDate
	@Column(name = "FRST_REGIST_DT", updatable = false, nullable = false)
	private Timestamp frstRegistDt;	// 등록일시
	
	@Column(name = "LAST_REGIST_MEMB_SN")
	private Long lastRegistMembSn; 	// 수정회원번호 , length = 10
	// updatable = true, nullable = true 얘네는 true 기본이라 안써도 됨
	
	@LastModifiedDate
	@Column(name = "LAST_CHANGE_DT", updatable = true, nullable = true)
	private Timestamp lastChangeDt; // 수정일시
	
//	protected CommonData(Long frstRegistMembSn, Long lastRegistMembSn) {
//		this.frstRegistMembSn = frstRegistMembSn;
//		this.lastRegistMembSn = lastRegistMembSn;
//	}

	protected CommonData(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
			Timestamp lastChangeDt) {
		this.useYn = useYn;
		this.frstRegistMembSn = frstRegistMembSn;
		this.frstRegistDt = frstRegistDt;
		this.lastRegistMembSn = lastRegistMembSn;
		this.lastChangeDt = lastChangeDt;
	}
	
	
}
