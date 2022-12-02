package net.e4net.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.e4net.demo.entity.Money;
import net.e4net.demo.entity.Member;
import net.e4net.demo.entity.MembCls;
import net.e4net.demo.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class DemoApplicationTests {
	@Autowired
	MemberRepository repository;

	@Test
    @Transactional
    @Rollback(false)
    public void testMember() {
//        Member member = new Member(
//                123, UserRole.USER, "10", "testid", 
//                "testpwd", "testnm", "010-1234-1234",
//                "test@naver.com", "12345", "서울시 강남구",
//                "1500호", "2022-11-08-20:11:11"
//        );
		Member member = Member.builder()
						.membSn(123l)
						.build();
		
        repository.save(member);

//        Member findMember = repository.findByMembNm(member.getMembNm());

//        assertEquals(findMember.getId(), member.getId());
//        assertEquals(findMember.getNickname(), member.getNickname());
//        assertEquals(findMember.getRole(), member.getRole());
//        assertEquals(findMember, member);
    }
	
	
	//=============== 교육 실습 ===============
	@Test
	void contextLoads() {
	}
	
	@Test
	void MembMoneyTest() {
		// given
//		Member testMember = repository.save(Member.createMember("Role_admin"));
//		MembMoney testMembMoney = MembMoney.createMembMoney(testMember);
		
		// when
		
		// then 
//		assertTrue(testMember.getMembSn().equals(1));
//		assertTrue(testMembMoney.getMoneySn().equals(1));
		
	}

}
