package net.e4net.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.dto.MailDTO;
import net.e4net.demo.entity.Member;
import net.e4net.demo.repository.MemberRepository;

@Slf4j
@Service
@AllArgsConstructor
public class SendEmailService {
	
	@Autowired
	MemberRepository memberRepository;
	
	private JavaMailSender mailSender;
	private static final String FROM_ADDRESS = "본인의 이메일 주소를 입력하세요!";
	
	public MailDTO createMailAndChangePassword(String userEmail, String membId){
        String str = getTempPassword();
        MailDTO dto = new MailDTO();
        dto.setAddress(userEmail);
        dto.setTitle(membId+"님의 HOTTHINK 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. HOTTHINK 임시비밀번호 안내 관련 이메일 입니다." + "[" + membId + "]" +"님의 임시 비밀번호는 "
        + str + " 입니다.");
        updatePassword(str,userEmail);
        return dto;
    }

    public void updatePassword(String str,String membId){
        String pw = EncryptionUtils.encryptMD5(str);
        Optional<Member> member = memberRepository.findByMembId(membId);
//        memberRepository.updateUserPassword(membId,pw);
        member.get().setMembPwd(pw);
    }


    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        String str = "";
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
    
    public void mailSend(MailDTO mailDto){
    	log.debug("SendEmailService :: \n	mailDto => {}", mailDto.toString());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());			// 받는사람 주소
        message.setFrom(SendEmailService.FROM_ADDRESS);	// 보내는 사람 주소  (설정하지 않으면 디폴트 값인  yml에 작성한 username) 
        message.setSubject(mailDto.getTitle());			// 메일 제목
        message.setText(mailDto.getMessage());			// 메일 내용 

        mailSender.send(message);
        log.debug("\n	이메일 전송 성공!!");
    }
    
    
}
