import React, { useCallback, useEffect, useState } from 'react';
import '../css/Join2.css';
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Form, InputGroup, DropdownButton, Dropdown } 
    from 'react-bootstrap';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Post from './component/Post';
import DaumPost from 'react-daum-postcode';
import PopupDom from './component/PopupDom';
import PopupPostCode from './component/PopupPostCode';

function Join2(props) {
    const navigate = useNavigate();  
    const [email1, setEmail1] = useState("");
    const [email2, setEmail2] = useState("");
    const [confirmPassword, setConfirmPassword] = useState(""); 
    const [userCerNum, setUserCerNum] = useState("");
    const [resCerNum, setResCerNum] = useState(0);
    const [phoneChkVal, setPhoneChkVal] = useState(false);
    const [idChkVal, setIdChkVal] = useState(false);
    const [membCls, setMembCls] = useState("");

    const [val, setVal] = useState({    // 이게 dto 라고 생각하면 된다!
        membId:"",
        membPwd:"",
        membNm: "",
        detailAddr: "",
        zipCd: "",
        zipAddr:"",
        membCls: "ROLE_USER",
    })
    const [fulladdress, setfullAddress] = useState("");
    const [extraaddress, setextraAddress] = useState({});

    const changeValue = (e) => {
        console.log(e.target.name, e.target.value);
        setVal({
            ...val, 
            [e.target.name]: e.target.value,
        });
        console.log(val.membId);
    }

    const onUserCerNumHandler = (event) => {
        setUserCerNum(event.currentTarget.value);
    }
    const onConfirmPasswordHandler = (event) => {
        setConfirmPassword(event.currentTarget.value);
    }

    const [enroll_company, setEnroll_company] = useState({
        address:'',
    });

    // 팝업창 상태 관리
    const [isPopupOpen, setIsPopupOpen] = useState(false)
	// 팝업창 열기
    const openPostCode = () => {
        setIsPopupOpen(true)
    }
	// 팝업창 닫기
    const closePostCode = () => {
        setIsPopupOpen(false)
    }
    
    const handleInput = (e) => {
        setEnroll_company({
            ...enroll_company,
            [e.target.name]:e.target.value,
        })
    }
    const handleChage2 = (data) => {
        setfullAddress(
              data
        );
        setVal({
            ...val,
            zipCd : data,
        });
    };
    const handleChage3 = (e) => {
        const {name, value} = e.target;
        setextraAddress({
            ...extraaddress,
            [name] : value,
        });
    };

    
    const [zonecode, setZonecode] = useState("");

    /* =============== email =============== */
    const firstEmail = useCallback((e) => {
        e.preventDefault();
        setEmail1(e.target.value);
    }, []);
    const secondEmail = useCallback((e) => {
        setEmail2(e.target.value); // ex) @naver.com
    }, []);
    const emailAddr = email1 + email2;
    console.log('emailAddr =>',emailAddr); //ex) hello@naver.com
    
    useEffect(()=> {
        console.log("============ 회원가입 페이지 ============ ");
        setMembCls("ROLE_USER");    // 회원가입 페이지 실행 시, 회원구분을 user 로 기본값 설정 
    }, []);

    useEffect(()=> {
        console.log(`미르야 침착해!!`);
        // setZonecode(zonecode);    
        setVal({
            ...val,
            zipCd : zonecode,
            zipAddr : fulladdress,
        }); 
    }, [zonecode, fulladdress]);

    console.log(`zipCd => ${val.zipCd}`);
    useEffect(() => {
        setVal({
            ...val, // 스프레드 연산자 
            emailAddr: emailAddr,
            membCls: membCls
        })
    }, [emailAddr, membCls]);

    // =============== 회원구분 클릭 시 ===============
    const handleChange = e => {
        // e.preventDefault();  // <= 이걸 하면 라디오 클릭이 이상함 (두번 클릭해야 됨)
        const { name, value } = e.target;
        // setMembCls({
        //   [name]: value  // <= 이렇게 하면 membCls.membCls 이렇게 가져와야 됨 ㅠ
        // });  
        setMembCls(e.target.value);
    };
    console.log('h membCls => ', membCls); // useEffect 보다 한 박자 느린 듯..? 그래서 자꾸 반대로 콘솔 찍히는 것 같은데
    useEffect(() => {
        // setMembCls(membCls);
        console.log('membCls => ', membCls);
        console.log('=============================');
    }, [membCls]);




    // =============== 회원가입 - 중복확인 버튼 ===============
    const onIdChkHandler = (event) => {
        event.preventDefault();
        console.log('중복확인 버튼 click!');

        axios.get("http://localhost:8888/member/exists/"+val.membId
        ).then((res) => {
            console.log(res.data);
            if(res.data == false) {
                alert("이미 존재하는 ID 입니다.")                     
            } else{
                alert("사용 가능한 ID 입니다.")
                setIdChkVal(true);
        }})

    }

    
    // =============== 회원가입 버튼 ===============
    const onSubmitHandler = (event) => {
        event.preventDefault();
        console.log('회원가입 버튼 click!');

        if(idChkVal == false){
            alert("아이디 중복 확인을 해주세요");
            return;
        }
        if (phoneChkVal == false){
            alert("휴대폰 번호 인증 확인을 해주세요");
            return;
        }
        if(idChkVal == true || phoneChkVal == true){
            console.log('idChkVal => ', idChkVal);
            console.log('phoneChkVal => ', phoneChkVal);
            console.log('val.membCls => ', val.membCls);
            axios.post("http://localhost:8888/auth/signup", val, {
            headers: {
                "Content-Type": "application/json",
                }
            }).then((res) => {
                console.log(res.data);
                if(confirmPassword !== val.membPwd) {
                    alert("비밀번호와 비밀번호 확인이 일치하지 않습니다")                     
                } else{
                    alert("회원가입 완료")
                    navigate("/")
            }})
            
        }
    }

    // =============== coolsms 휴대폰번호 인증번호 "전송" 버튼 ===============
    const onCerNumSendHandler = (event) => {
        event.preventDefault();
        console.log('인증번호 전송 버튼 click!');
        console.log('Phone => '+val.mobileNo);

        axios.get("http://localhost:8888/check/sendSMS/" + val.mobileNo,
        {
        // headers: {
        //     "Content-Type": "application/json",
        //     }
        }).then((res) => { // 인증번호 반환값 
            console.log("res => "+res);
            console.log("typeof(res) => "+ typeof(res));
            console.log("res.data '인증번호 반환값' => "+ res.data);
            console.log("typeof(res.data) => "+ typeof(res.data));

            setResCerNum(res.data);
        })
    }

    // =============== coolsms 휴대폰번호 인증번호 "확인" 버튼 ===============
    const onCerNumChkHandler = (event) => {
        event.preventDefault();
        console.log('인증번호 확!인! 버튼 click!');
        console.log('UserCerNum => '+ userCerNum);
        console.log('typeof(UserCerNum) => '+ typeof(userCerNum));
        console.log('ResCerNum => '+ resCerNum);
        console.log('typeof(resCerNum) => '+ typeof(resCerNum));

        if(userCerNum == resCerNum){
            setPhoneChkVal(true);
            alert("인증되었습니다.")
        }else{
            alert("인증번호가 일치하지 않습니다.")
        }
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////
    return (
        <div className='container'
            style={{
                margin: '50px ',
                width: '100vw',
                height: '100vh',
            }}
            >
            <div style={{backgroundColor:'#dadada', borderRadius:'7px'}}>
            <h1 style={{padding:'8px'}}>&nbsp;회원가입</h1></div>
            <Form className='container'>
                <h4 >▶ 회원정보</h4>
                {/* 일반 회원 , 판매자 라디오 박스 추가 */}
                <div className="form-check form-check-inline">
                    <input value="ROLE_USER" onChange={handleChange} defaultChecked className="form-check-input" 
                        type="radio" name="membCls" id="membCls"  />
                    <label className="form-check-label" htmlFor="inlineRadio1">일반 회원</label>
                </div>
                <div className="form-check form-check-inline">
                    <input value="ROLE_SELLER" onChange={handleChange} className="form-check-input" 
                        type="radio" name="membCls" id="membCls"  />
                    <label className="form-check-label" htmlFor="inlineRadio2">판매자</label>
                </div>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label >아이디 (*)</Form.Label>
                    <div className='inputGroup'>
                        <Form.Control type="text" name='membId' value={val.membId} onChange={changeValue} style={{marginRight:'10px'}}/>
                        {/* 중복확인 버튼 옆에 두고 싶은데   style={{float:'left'}}   안 먹음 ㅠ */}
                        <Button variant="primary" type="button" style={{width:'100px'}} onClick={onIdChkHandler} >
                            중복확인
                        </Button>
                    </div>
                        {/* <Form.Text className="text-muted">
                        We'll never share your email with anyone else.
                        </Form.Text> */}
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>비밀번호 (*)</Form.Label>
                    <div className='inputGroup'>
                    <Form.Control type="password" value={val.membPwd} name="membPwd" onChange={changeValue}/>
                    </div>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>비밀번호 확인 (*)</Form.Label>
                    <Form.Control type="password" value={confirmPassword} onChange={onConfirmPasswordHandler}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>성명 (*)</Form.Label>
                    <Form.Control type="text" name='membNm' value={val.membNm} onChange={changeValue}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>휴대폰 번호 (*)</Form.Label>
                    <div className='inputGroup'>
                        <Form.Control type="text" name='mobileNo' value={val.mobileNo} onChange={changeValue}
                            placeholder="- 없이 숫자만 입력하세요." style={{ marginRight:'10px'}}/>
                        <Button variant="primary" type="button" style={{width:'180px'}} onClick={onCerNumSendHandler}>
                            인증번호 전송
                        </Button>
                    </div>
                    <div className='inputGroup'>
                        <Form.Control type="text" value={userCerNum} onChange={onUserCerNumHandler}
                            placeholder="인증번호 입력" style={{ marginRight:'10px'}}/>
                        <Button variant="primary" type="button" style={{width:'180px'}} onClick={onCerNumChkHandler} >
                            인증번호 확인
                        </Button>
                    </div>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>이메일</Form.Label>
                    <InputGroup className="mb-3" style={{alignItems:'center'}} >
                        <Form.Control aria-label="Text input with dropdown button" value={email1} onChange={firstEmail}/> @ 
                        <Form.Select aria-label="Default select example" value={email2} onChange={secondEmail}>
                            <option style={{textAlign:'center'}}>-----  선택  -----</option>
                            <option value="@naver.com">naver.com</option>
                            <option value="@daum.net">daum.net</option>
                            <option value="@gmail.com">gmail.com</option>
                        </Form.Select>
                    </InputGroup>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>우편번호 (*)</Form.Label>
                    <div className='inputGroup'>
                    <Form.Control type="text" name='zipCd' value={val.zipCd} 
                        onChange={changeValue} style={{ marginRight:'10px'}}/>
                    {/* <input type="text" value={extraaddress} /> */}
                    </div>
                    </Form.Group>
                {/* <button onClick={openpost}>우편번호 찾기</button> */}
                {/* 버튼 클릭 시 팝업 생성 */}
                <button className='btn btn-primary' type='button'  style={{width:'180px'}}
                    onClick={openPostCode}>우편번호 검색</button>
                {/* 팝업 생성 기준 div */}
                <div id='popupDom'>
                    {isPopupOpen && (
                        <PopupDom>
                            <PopupPostCode zonecode={zonecode} setZonecode={setZonecode} 
                                fulladdress={fulladdress} setfullAddress={setfullAddress} onClose={closePostCode} />
                        </PopupDom>
                    )}
                </div>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>주소 (*)</Form.Label>
                    <Form.Control type="text" name='zipAddr' value={val.zipAddr} onChange={changeValue}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>상세주소</Form.Label>
                    <Form.Control type="text" name='detailAddr' value={val.detailAddr} onChange={changeValue}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicCheckbox">
                    <Form.Check type="checkbox" label="Check me out" />
                </Form.Group>
                {/* <div class="col-lg-6 col-sm-12 text-lg-end text-center"> */}
                <div className='form-row float-right'>
                <Button variant="primary" type="submit" className='joinBtn' onClick={onSubmitHandler} >
                    회원가입
                </Button>
                </div>
            </Form>
        </div>
    )
}

export default Join2;