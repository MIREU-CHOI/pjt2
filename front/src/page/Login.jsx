import axios from "axios";
import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import '../css/login.css';

const User = {
    id: 'test123',
    pw: 'test123'
}

function Login() {
    const navigate = useNavigate();
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');

    const [idValid, setIdValid] = useState(false);
    const [pwValid, setPwValid] = useState(false);
    const [notAllow, setNotAllow] = useState(true);

    const register =() =>{
        navigate("/join");
    }
    const signIn =() =>{
        navigate("/charge");
    }

    const handleId = (e) => {
        setId(e.target.value);
        // const regex = '';
            // /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
        // if(regex.test(id)) {
        // if(setId(e.target.value)) {
        //     setIdValid(true);
        // } else {
        //     setIdValid(false);
        // }
    }

    const handlePassword = (e) => {
        setPw(e.target.value);
        // const regex = '';
            // /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
        // if(regex.test(pw)) {
        // if(setPw(e.target.value)) {
        //     setPwValid(true);
        // } else {
        //     setPwValid(false);
        // }
    }

    const onClickConfirmButton = () => {
        if(id === User.id && pw === User.pw){
            alert('로그인에 성공했습니다.');
            navigate("/charge");
        } 
        // else if (id !== User.id ){
        //     alert('등록되지 않은 ID입니다.')
        // } else if (pw !== User.pw){
        //     alert('비밀번호가 올바르지 않습니다.')
        // } 
        else {
            if(id.length == 0){
                alert('ID를 입력하시기 바랍니다.')
            } else if (pw.length == 0 ){
                alert('비밀번호를 입력하시기 바랍니다.')
            }
        }
    }
    // const onClickConfirmButton = (e) => {
    //     e.preventDefault();
    //     axios.post("/login",{
    //         id: idRef.current.value,
    //         pwd: pwdRef.current.value, 
    //     },{"Content-Type": 'application/json'})
    //     .then( response => {
    //         setLoginStatus(true);
    //         navigate("/main/chargeCash");
    //     }).catch(err => {
    //         console.log(`Error Occured!!! =>${err.code}`);
    //         if (err.code === "ERR_BAD_REQUEST"){
    //             countUp(1);
    //         }
    //     })
    // }

    // useEffect(() => {
    //     if(idValid && pwValid){
    //         setNotAllow(false);
    //         return;
    //     }
    //     setNotAllow(true);
    // }, [idValid, pwValid]);

    const onLoginHandler = (event) => {
        event.preventDefault();
        console.log('로그인 버튼 click!');
        let body = {
            membId: id,
            membPwd: pw
        }
        const data = JSON.stringify(body);

        axios.post("http://localhost:8888/auth/login", data, {
        headers: {
            "Content-Type": "application/json",
        }
        })
        // .then(res => res.json())  // axios 는 따로 json 변환할 필요 없음!
        .then(res => {
        // console.log('typeof(res) =>', typeof(res));
        // console.log('typeof(resjson()) =>', typeof(res.json()));
            console.log('res.data => ',res.data);
            localStorage.setItem('accessToken', res.data.accessToken);
            console.log('accessToken => ',localStorage.getItem("accessToken"));
            sessionStorage.setItem('membSn', res.data.membSn);
            sessionStorage.setItem('membCls', res.data.membCls);
            sessionStorage.setItem('membId', res.data.membId);
            console.log('membSn => ', sessionStorage.getItem("membSn"));
            console.log('membCls => ', sessionStorage.getItem("membCls"));
            console.log('membId => ', sessionStorage.getItem("membId"));
            navigate("/charge")
            
        })
    }

    




    return (
        <div className="page">
            <div className="titleWrap">E4. Pay Service</div>
            <div className="titleWrap2">
                서비스 이용을 위해서는 로그인이 필요합니다. 
            </div>

            <div className="contentWrap">
                <div className="inputTitle">ID</div>
                <div className="inputWrap">
                    <input 
                      type='text'
                      className="input" 
                      placeholder="ID"
                      value={id} 
                      onChange={handleId} // (e) => setId(e.target.value)
                    />
                </div>
                {/* <div className="errorMessageWrap">
                    {   !idValid && id.length < 1 && (
                            <div>ID를 입력하시기 바랍니다. </div>
                    )}
                </div> */}

                <div style={{marginTop: "26px"}} className="inputTitle">
                    PWD</div>
                <div className="inputWrap">
                    <input 
                      type='Password'
                      className="input" 
                      placeholder="Password"
                      value={pw} 
                      onChange={handlePassword}
                    />
                </div>
                {/* <div className="errorMessageWrap">
                    {   !pwValid && pw.length < 1 && (
                            <div>비밀번호를 입력하시기 바랍니다. </div>
                    )}
                </div> */}
            </div>

            <div>
                {/* <button onClick={onClickConfirmButton}  className="loginButton">Login</button> */}
                <button onClick={onLoginHandler}  className="loginButton">Login</button>
            </div>
            <div>
                <button onClick={register}  className="login_registerButton">회원가입</button>
            </div>
        </div>
    )
}

export default Login;