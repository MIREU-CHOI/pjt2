package net.e4net.demo.service;

import java.util.HashMap;
import java.util.Random;

import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.repository.MemberRepository;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CertificationService {

	public String certifiedPhoneNumber(String mobileNo) {

        String api_key = "NCSWHPQVP5NAUBVJ";
        String api_secret = "L8XDZSRGW60ZWXZYAVWBVOMMLMWBQVXW";
        Message coolsms = new Message(api_key, api_secret);
        
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }
        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", mobileNo);    		// 수신전화번호
        params.put("from", "01050949760");  // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "E4net Pay Service 휴대폰인증 테스트 메시지 : 인증번호는 " + "[ "+numStr+" ]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params); // 메시지 전송 
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
        return numStr;

    }
}
