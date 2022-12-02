package net.e4net.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO { // 비밀번호를 수정할 때 쓰이는 dto
	private String membId;
    private String exMembPwd;
    private String newMembPwd;
    
    // 이전의 비밀번호가 제대로 입력하지 않는다면 실행되지 않는다.
}
