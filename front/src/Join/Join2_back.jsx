import React, { useCallback, useEffect, useState } from 'react';
import '../css/Join2.css';
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Form, InputGroup, DropdownButton, Dropdown } 
    from 'react-bootstrap';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Join2_back(props) {
    const navigate = useNavigate();  
    // const [val, setVal] = useState("");
    const [membId, setMembId] = useState("");
    const [password, setPassword] = useState("");
    const [email1, setEmail1] = useState("");
    const [email2, setEmail2] = useState("");
    const [phone, setPhone] = useState("");
    const [zipCd, setZipCd] = useState("");
    const [zipAddr, setZipAddr] = useState("");
    const [detailAddr, setDetailAddr] = useState("");
    const [membNm, setMembNm] = useState("");
    const [confirmPassword, setConfirmPassword] = useState(""); 
    const [userCerNum, setUserCerNum] = useState("");
    const [resCerNum, setResCerNum] = useState(0);
    const [phoneChkVal, setPhoneChkVal] = useState(false);
    const [idChkVal, setIdChkVal] = useState(false);
    const [membCls, setMembCls] = useState("");

    // const [member, setMember] = useState([
    //     {
    //         id: "Arsen Lüpin",
    //         pwd : "4444",
    //         name : "아르센 뤼팽",
    //     },
    // ]);
    const [val, setVal] = useState({    // 이게 dto 라고 생각하면 된다!
        membId:"",
        membPwd:"",
        membNm: "",
        detailAddr: "",
    })
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
    const onMembIdHandler = (event) => {
        setMembId(event.currentTarget.value);
    }
    const onPasswordHandler = (event) => {
        setPassword(event.currentTarget.value);
    }
    const onConfirmPasswordHandler = (event) => {
        setConfirmPassword(event.currentTarget.value);
    }
    const onPhoneHandler = (event) => {
        setPhone(event.currentTarget.value);
    }
    const onZipCdHandler = (event) => {
        setZipCd(event.currentTarget.value);
    }
    const onZipAddrHandler = (event) => {
        setZipAddr(event.currentTarget.value);
    }
    const onDetailAddrHandler = (event) => {
        setDetailAddr(event.currentTarget.value);
    }
    const onMembNmHandler = (event) => {
        setMembNm(event.currentTarget.value);
    }

    /* email */
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
        setMembCls("ROLE_USER");
    }, []);

    useEffect(() => {
        setVal({
            ...val, // 스프레드 연산자 
            emailAddr: emailAddr,
        })
    }, [emailAddr]);

    // --------------------- 회원구분 클릭 시 ------------------------
    const handleChange = e => {
        // e.preventDefault();  // <= 이걸 하면 라디오 클릭이 이상함 (두번 클릭해야 됨)
        const { name, value } = e.target;
        setMembCls({
          [name]: value
        });
    };
    console.log('h membCls => ', membCls); // useEffect 보다 한 박자 느린 듯..? 그래서 자꾸 반대로 콘솔 찍히는 것 같은데
    useEffect(() => {
        // setMembCls(membCls);
        console.log('membCls => ', membCls);
        console.log('membCls.membCls => ', membCls.membCls);
        console.log('typeof membCls => ', typeof(membCls.membCls));
        console.log('=============================');
    }, [membCls]);




    // =============== 회원가입 - 중복확인 버튼 ===============
    const onIdChkHandler = (event) => {
        event.preventDefault();
        console.log('중복확인 버튼 click!');

        axios.get("http://localhost:8888/member/exists/"+membId
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

        let body = {
            membId: membId,
            membPwd: password,
            membNm: membNm,
            mobileNo: phone,
            // email: Email,
            emailAddr: emailAddr,
            zipCd: zipCd,
            zipAddr: zipAddr,
            detailAddr: detailAddr,
            membCls: membCls.membCls
        }
        const data = JSON.stringify(body);
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
            axios.post("http://localhost:8888/auth/signup", data, {
            headers: {
                "Content-Type": "application/json",
                }
            }).then((res) => {
                console.log(res.data);
                if(confirmPassword !== password) {
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
        console.log('Phone => '+phone);

        axios.get("http://localhost:8888/check/sendSMS/" + phone,
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
        // style={{ 
        //     display: 'flex', justifyContent: 'center', alignItems: 'center', 
        //     width: '100%', height: '100vh'
        //     }}
        style={{
            margin: '50px ',
        //     display: 'flex',
        //     justifyContent: 'center',
        //     alignItems: 'center',
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
                        <Form.Control type="text"  value={membId} onChange={onMembIdHandler} style={{marginRight:'10px'}}/>
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
                    <Form.Control type="password" value={password} name={password} onChange={onPasswordHandler}/>
                    </div>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>비밀번호 확인 (*)</Form.Label>
                    <Form.Control type="password" value={confirmPassword} onChange={onConfirmPasswordHandler}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>성명 (*)</Form.Label>
                    <Form.Control type="text" value={membNm} onChange={onMembNmHandler}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>휴대폰 번호 (*)</Form.Label>
                    <div className='inputGroup'>
                        <Form.Control type="text" value={phone} onChange={onPhoneHandler}
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
                    <Form.Control type="text" value={zipCd} onChange={onZipCdHandler}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>주소 (*)</Form.Label>
                    <Form.Control type="text" value={zipAddr} onChange={onZipAddrHandler}/>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>상세주소</Form.Label>
                    <Form.Control type="text" value={detailAddr} onChange={onDetailAddrHandler}/>
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

export default Join2_back;