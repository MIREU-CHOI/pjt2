package net.e4net.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.dto.MailDTO;
import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.entity.Member;
import net.e4net.demo.repository.MemberRepository;

@Slf4j
@Service
@AllArgsConstructor
//@RequiredArgsConstructor
public class SendEmailService {
	
	@Autowired
	MemberRepository memberRepository;
	
	private JavaMailSender mailSender;
	private static final String FROM_ADDRESS = "alfmsp123@naver.com";
    private final PasswordEncoder passwordEncoder;
	
	public MailDTO createMailAndChangePassword(MemberDTO dto){
        log.debug("SendEmailService\n	메일 작성 & 비번 변경하자");
        String membId = dto.getMembId();
        String emailAddr = dto.getEmailAddr();
        String str = getTempPassword();
        MailDTO mailDto = new MailDTO();
        mailDto.setAddress(emailAddr);
        mailDto.setTitle(membId+"님의 E4net 임시비밀번호 안내 이메일 입니다.");
        mailDto.setMessage("안녕하세요. E4net 임시비밀번호 안내 관련 이메일 입니다." 
        				+ "[" + membId + "]" +"님의 임시 비밀번호는 "+ str + " 입니다.");
        updatePassword(str, dto);
        return mailDto;
    }

    public void updatePassword(String str, MemberDTO dto){
        log.debug("SendEmailService\n	updatePassword call!!");
//        String pw = EncryptionUtils.encryptMD5(str);
        String pwd = passwordEncoder.encode(str);
        Optional<Member> memb = memberRepository.findByMembId(dto.getMembId());
        Member member = memberRepository.findById(memb.get().getMembSn()).orElse(null);
        log.debug("SendEmailService updatePassword\n	"
        		+ "비번 바꿀 MembSn => {}\n	바뀐 비번 => {}"
        		, member.getMembSn(), pwd);
        member.setMembPwd(pwd);
        memberRepository.save(member);
    }


    public String getTempPassword(){
        log.debug("\n	이메일 전송 성공!!");
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
    	log.debug("SendEmailService :: \n	to => {} \n	from => {}", mailDto.getAddress(), FROM_ADDRESS);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());			// 받는사람 주소
        message.setFrom(SendEmailService.FROM_ADDRESS);	// 보내는 사람 주소  (설정하지 않으면 디폴트 값인  yml에 작성한 username) 
        message.setSubject(mailDto.getTitle());			// 메일 제목
        message.setText(mailDto.getMessage());			// 메일 내용 

        mailSender.send(message);
        log.debug("\n	이메일 전송 성공!!");
    }
    
    
}






