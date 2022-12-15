import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Button,  Table } from 'react-bootstrap';
import '../css/myPage.css';


//////////////////////////////////////////////////////////////////////////////////////////////////

const MyPage = (props) => {
    
    const [test, setTest] = useState();
    const [user, setUser] = useState();
    const [currMembPwd, setCurrMembPwd] = useState(""); 
    const [membPwd, setMembPwd] = useState(""); 
    const [confirmPassword, setConfirmPassword] = useState(""); 
    const membSn = sessionStorage.getItem('membSn');

    const onCurrMembPwdHandler = (event) => {
        setCurrMembPwd(event.currentTarget.value);
    }
    const onMembPwdHandler = (event) => {
        setMembPwd(event.currentTarget.value);
    }
    const onConfirmPasswordHandler = (event) => {
        setConfirmPassword(event.currentTarget.value);
    }

    // ================ 결제 페이지로 올 때 "가맹점" 정보 가져오기 ================
    useEffect(() => {
        console.log("============ MyPage ============ ");
        axios.get(global.ipAddress+":8888/member/"+membSn, 
        {
        }).then((res) => {
            // console.log('typeof(res) =>', typeof(res))
            // console.log('res =>', res)
            // console.log('typeof(res.data) =>', typeof(res.data))
            // console.log('res.data =>', res.data)
            setUser(res.data);
        }).catch((error) => {
            console.log(error);
        })
    }, []);

    const onUpdatePwd = (e) => {
        // if(currMembPwd != user.membPwd){
        //     alert('현재 비밀번호가 일치하지 않습니다.');
        //     return;
        // }    
        if(membPwd != confirmPassword){
            alert('입력하신 새 비밀번호와 확인이 일치하지 않습니다.');
            return;
        }
        let bool = window.confirm('비밀번호를 변경하시겠습니까?');
        if(bool){
            if(membPwd == confirmPassword) {
                let data = {
                    membSn : membSn,
                    membPwd : membPwd
                }
                axios.post(global.ipAddress+":8888/member/update", data, {
                    // headers: {
                    //     "Content-Type": "application/json",
                    //     }
                    }).then((res) => {
                        console.log(res.data);
                        alert('비밀번호 변경 완료되었습니다.');
                        setMembPwd(() => "");
                        setConfirmPassword(() => "");
                    }).catch((error) => {
                        console.log(error);
                })
            }
        }
    }




//////////////////////////////////////////////////////////////////////////////////////////////////

    return (
        <div className='Container'>
          <form className='container'>
            <Table >
              <thead>
                <tr>
                {/* 첫 마운트 될때는 데이터가 undefined 여서 에러뜸
             => && 니까 왼오 모두 true 여야 실행됨(데이터 보여짐) */}
                  <th>
                    {
                        user !== undefined && user.membCls == 'ROLE_USER'
                        ? '일반 회원'
                        : user !== undefined && user.membCls == 'ROLE_SELLER'
                        ? '판매자'
                        : ''
                    }
                  </th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>ID</td>
                  <td>{user !== undefined && user.membId}</td>
                </tr>
                <tr>
                  <td>새 비밀번호</td>
                  <td>
                    <input value={membPwd} id='membPwd' name='membPwd' 
                        type='password' onChange={onMembPwdHandler} />
                  </td>
                </tr>
                <tr>
                  <td>비밀번호 확인</td>
                  <td>
                    <input value={confirmPassword} id='confirmPassword' name='confirmPassword' 
                        type='password' onChange={onConfirmPasswordHandler} />
                  </td>
                </tr>
                <tr>
                  <td>성명</td>
                  <td>
                    {user !== undefined && user.membNm}
                  </td>
                </tr>
                <tr>
                  <td>휴대폰 번호</td>
                  <td>
                    {user !== undefined && user.mobileNo}
                  </td>
                </tr>
                <tr>
                  <td>이메일</td>
                  <td style={{fontSize:'14px'}} id='email' >
                    {user !== undefined && user.emailAddr}
                  </td>
                </tr>
                <tr>
                  <td>우편번호</td>
                  <td>
                    {user !== undefined && user.zipCd}
                  </td>
                </tr>
                <tr>
                  <td>주소</td>
                  <td id='fullAddr'>
                    {user !== undefined && user.zipAddr}
                    <br/>
                    {user !== undefined && user.detailAddr}
                  </td>
                </tr>
              </tbody>
            </Table>
            <div style={{textAlign:'center'}}>
                <Button 
                    onClick={onUpdatePwd} type="button"
                    className='btn_upInfo w-btn-outline w-btn-pink-outline'>
                    수정
                </Button> 
            </div>
          </form>
        </div>
    );
};

export default MyPage;