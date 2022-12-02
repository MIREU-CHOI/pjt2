package net.e4net.demo.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CommonDTO {

	private String useYn;
	private Long frstRegistMembSn;
	private Timestamp frstRegistDt;
	private Long lastRegistMembSn;
	private Timestamp lastRegistDt; 
	
}
