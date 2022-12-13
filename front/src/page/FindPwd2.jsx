import axios from 'axios';
import React, { useState } from 'react';
import Swal from 'sweetalert2';
import {  useNavigate } from 'react-router-dom';
import '../css/findPwd2.css';

const FindPwd2 = () => {

  // const [userEmail, setUserEmail] = useState();
  // const [userId, setUserId] = useState();

  const navigate = useNavigate();
  const [val, setVal] = useState({
    userEmail : "",
    userId : "",
  })
  const changeValue = (e) => {
    console.log(`value => ${e.target.value}`);
    setVal({
      ...val, 
      [e.target.name]: e.target.value,
    });
  }

// ================ 비번 찾기 - OK 버튼 ================
  const clickOK = (e) => {
      e.preventDefault();
      console.log('onChkEmail !!');
      console.log(`userId => ${val.userId} \n userEmail => ${val.userEmail}`);
      axios.get(global.ipAddress+":8888/member/findPwd/"
                +val.userId+"/"+val.userEmail
      ).then((res) => {
        console.log('res.data.check =>', res.data.check);
        if (res.data.check){  
          // data 가 true 면! (아이디와 이메일 일치했다는 뜻)
          // clickOKCallback 호출해서 이메일 보내자
          console.log('typeof(res) => ', typeof(res));
          console.log('res => ', res);
          console.log('res.data => ', res.data);
          clickOKCallback(); 
          
          
        }else{
          alert('아이디와 이메일이 일치하지 않습니다. \n다시 입력해주세요.');
        }
      })
    };
    // 콜백 ~~~~~
    const clickOKCallback = (e) => {
      let data = {
        membId : val.userId,
        emailAddr : val.userEmail
      }
      console.log('clickOKCallback~~~~~~~~~~\n  data => {}',data);
      axios.post(global.ipAddress+":8888/member/findPwd/sendEmail"
                , data
                , { headers: {"Content-Type": "application/json", }}
      ).then(res => {
        Swal.fire({
          icon: "success",
          title: "발송 완료!",
          text: "입력하신 이메일로 임시비밀번호가 발송되었습니다.",
          showCancelButton: true,
          confirmButtonText: "확인",
          // cancelButtonText: "취소",
        }).then((res) => {
          /* Read more about isConfirmed, isDenied below */
          if (res.isConfirmed) {  // user가 확인을 누르면 true 반환!
            console.log('typeof(res) => ', typeof(res));
            console.log('res => ', res);
            console.log('res.data => ', res.data);
            // if (OK){ // ok 하면 
            // }
            navigate('/');
          }
        });
      })
    };
    const clickCancel = (e) => {
      navigate('/');
    }




    // Swal.fire({
    //   icon: "warning",
    //   title: "삭제",
    //   text: `test 삭제 하시겠습니까??`,
    //   showCancelButton: true,
    //   confirmButtonText: "삭제",
    //   cancelButtonText: "취소",
    // }).then((res) => {
    //   /* Read more about isConfirmed, isDenied below */
    //   if (res.isConfirmed) {
    //       //삭제 요청 처리
    //       alert("test 성공!");
    //   }
    //   else{
    //       //취소
    //       alert("취소 누름");
    //   }
    // });

  


  

//////////////////////////////////////////////////////////////////////////////////////////////////

  return (
  <div>
    <div className="container">
    {/*  Modal  */}
    <div className='mod'
      // className="modal fade" 
      id="myModal" role="dialog">
        <div className="modal-dialog">

        {/*  Modal content  */}
        <div className="modal-content">
            <div className="modal-header" style={{padding:'35px 50px'}}>
                <h4><span className="glyphicon glyphicon-lock"></span> 비밀번호 찾기</h4>
            </div>
            <div className="modal-body" style={{padding:'40px 50px'}}>
                <div style={{color: "#ac2925", marginBottom:"10px"}}>
                    <center>입력된 정보로 임시 비밀번호가 전송됩니다.</center>
                </div>
                <form role="form">
                    <div className="form-group">
                        <label htmlFor="userId">
                          ID
                        </label>
                        <input type="text" className="form-control" 
                          id="userId" onChange={changeValue} name="userId" value={val.userId}
                          placeholder="가입 시 등록한 아이디를 입력하세요." />
                    </div>
                    <div style={{marginTop:'10px'}} className="form-group">
                        <label htmlFor="userEmail">
                          Email
                        </label>
                        <input type="text" className="form-control" 
                          id="userEmail" onChange={changeValue} name="userEmail" value={val.userEmail}
                          placeholder="가입 시 등록한 이메일을 입력하세요." />
                    </div>
                    <div style={{marginTop:'20px'}} className="btns">
                      <button onClick={clickOK} type="button" className="btn ok btn-success btn-block" >
                          OK
                      </button>
                      <button onClick={clickCancel} type="button" className="btn cancel btn-danger btn-default pull-left" >
                          Cancel
                      </button>
                    </div>
                </form>
                <div className="text-center small mt-2" id="checkMsg" style={{color:"red"}}></div>
            </div>
        </div>

        </div>
    </div>
    </div>
  </div>
  );
};

export default FindPwd2;