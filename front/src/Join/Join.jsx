import React, { useRef } from "react";
import { useEffect } from "react";
import { useState } from "react";
// import { useForm } from "react-demo";

const User = {
    id: 'test123',
    pw: 'test123'
}

export default function Join() {

    // const { register, handleSubmit, watch, errors } = useForm();
    const password = useRef();
    // password.current = watch("password");

    const onSubmit = (data) => {
        console.log('data', data)
    };

    return (
        <form 
        // onSubmit={handleSubmit(onSubmit)}
        >
        <label>Email</label>
        <input
            name="email"
            type="email"
            // ref={register({ required: true, pattern: /^\S+@\S+$/i })}
        />
        {/* {errors.email && <p>This email field is required</p>} */}

        <label>Name</label>
        <input
            name="name"
            // ref={register({ required: true, maxLength: 10 })}
        />
        {/* {errors.name && errors.name.type === "required"
            && <p> This name field is required</p>}
        {errors.name && errors.name.type === "maxLength"
            && <p> Your input exceed maximum length</p>} */}

        <label>Password</label>
        <input
            name="password"
            type="password"
            // ref={register({ required: true, minLength: 6 })}
        />
        {/* {errors.password && errors.password.type === "required"
            && <p> This name field is required</p>}
        {errors.password && errors.password.type === "minLength"
            && <p> Password must have at least 6 characters</p>} */}

        <label>Password Confirm</label>
        <input
            type="password"
            name="password_confirm"
            // ref={register({
            // required: true,
            // validate: (value) =>
            //     value === password.current
            // })}
        />
        {/* {errors.password_confirm && errors.password_confirm.type === "required"
            && <p> This password confirm field is required</p>}
        {errors.password_confirm && errors.password_confirm.type === "validate"
            && <p>The passwords do not match</p>} */}

        <input type="submit"
            style={{ marginTop: '40px' }}
        />

        </form>
    )
    
    // const [id, setId] = useState('');
    // const [pw, setPw] = useState('');

    // const [idValid, setIdValid] = useState(false);
    // const [pwValid, setPwValid] = useState(false);
    // const [notAllow, setNotAllow] = useState(true);


    // const handleId = (e) => {
    //     setId(e.target.value);
    // }

    // const handlePassword = (e) => {
    //     setPw(e.target.value);
    // }

    // return (
    //     <div className="page">
    //         <div className="titleWrap">회원가입</div>
    //         <div className="titleWrap2">
    //             {/* 서비스 이용을 위해서는 로그인이 필요합니다.  */}
    //         </div>

    //         <div className="contentWrap">
    //             <div className="inputTitle">ID</div>
    //             <div className="inputWrap">
    //                 <input 
    //                   type='text'
    //                   className="input" 
    //                   placeholder="ID"
    //                   value={id} 
    //                   onChange={handleId} // (e) => setId(e.target.value)
    //                 />
    //             </div>

    //             <div style={{marginTop: "26px"}} className="inputTitle">
    //                 PWD</div>
    //             <div className="inputWrap">
    //                 <input 
    //                   type='Password'
    //                   className="input" 
    //                   placeholder="Password"
    //                   value={pw} 
    //                   onChange={handlePassword}
    //                 />
    //             </div>
    //         </div>

    //         {/* <div>
    //             <button onClick={onClickConfirmButton}  className="loginButton">Login</button>
    //         </div>
    //         <div>
    //             <button onClick={onClickConfirmButton}  className="joinButton">회원가입</button>
    //         </div> */}
    //     </div>
    // )
}