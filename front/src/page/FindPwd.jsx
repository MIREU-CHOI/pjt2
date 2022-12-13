import axios from 'axios';
import React, { Component } from 'react';
import { Modal } from 'react-bootstrap';
import "../css/findPwd.css";

class FindPwd extends Component {
    constructor(props) {
        super(props);
        this.state = {
            result : false,
            secret : "",
            user_data : "",
            change : false,
        }
    }

    _searchPassword = async function() {
        const user_id = document.getElementsByName('search_pw_id')[0].value.trim();
    
        // 이메일 구하기
        const email_id = document.getElementsByName('search_pw_email')[0].value.trim();
        const email_host = document.getElementsByName('search_pw_write_email')[0].value.trim();
    
        const user_email = email_id + '@' + email_host;
    
        // 아이디 체크
        const id_check = /^[a-z]+[a-z0-9]{5,19}$/g;
    
        // 이메일 체크
        const email_check = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    
        if(!id_check.test(user_id)) {
          return alert('아이디는 영문자로 시작하는 6~20자여야만 합니다.')
        }
    
        if(email_id === "" || email_host === "") {
          return alert('이메일을 모두 입력해주세요.');
    
        } else if(!user_email.match(email_check)) {
          return alert('올바른 이메일 형식을 입력해주세요.');
        }
    
        const obj = { user_id : user_id, user_email : user_email }
        const res = await axios('/search/pw', {
          method : 'POST',
          data : obj,
          headers: new Headers()
        })
    
        if(res.data === false) {
          return alert('일치하는 데이터가 없습니다 \n다시 확인해주세요.');
        }
    
        document.getElementsByName('search_pw_id')[0].value = '';
    
        alert(res.data.result[0].email + '의 주소로 \n6자리의 숫자코드가 수신되었습니다.');
        return this.setState({ 
          result : true, 
          secret : res.data.secret, 
          user_data : res.data.result[0]
        })
      }
    
      _checkSecretCode = function() {
        const secret_code = Number(this.state.secret);
        const secret_input = Number(document.getElementsByName('pw_secret')[0].value.trim());
        
        if(String(secret_input).length !== 6) {
          return alert('6자리의 숫자코드를 입력해주세요.');
    
        } else if(secret_code !== secret_input) {
          return alert('숫자코드가 일치하지 않습니다.');
        }
    
        return this.setState({ change : true })
      }
    
      _changePassword = async function() {
        const change_password = document.getElementsByName('change_password')[0].value.trim();
        const check_change_password = document.getElementsByName('check_change_password')[0].value.trim();
    
        // 비밀번호 확인
        const pw_check = /^[a-z]+[a-z0-9]{5,19}$/g;
        if(!pw_check.test(change_password)) {
          return alert('비밀번호는 영문자로 시작하는 6~20자여야만 합니다.')
    
        } else if(change_password !== check_change_password) {
          return alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.')
        }
    
        const user_id = this.state.user_data.id;
        const obj = { user_id : user_id, change_password : change_password }
        await axios('/update/password', {
          method : 'POST',
          data : obj,
          headers: new Headers()
        })
    
        alert('비밀번호가 변경되었습니다.');
        this.setState({ result : false, change : false })
        return this.props._backSearchModal(this.props.target);
      }
    
      _resetPWResult = () => {
        this.setState({ result : false, change : false })
      }
    
      render() {
        const { 
          _closeSearchModal, _backSearchModal, target
        } = this.props;
    
        const { _resetPWResult } = this;
        const { result, user_data, change } = this.state;

    return (

        // <Modal></Modal>

        // 
        <div>
            <div className='Search_div'>
                <h4> 비밀번호 찾기 </h4>

                <div>  
                  <h5> 아이디 </h5>
                  <input type='text' maxLength='15' name='search_pw_id'/>
                </div>

                <div>  
                  <h5> 이메일 </h5>
                  <input type='text' maxLength='20' name='search_pw_email'/> 
                
                  <div id='search_id_email_div'>
                    @
                    <input type='text' maxLength='15' name='search_pw_write_email'/>
                  </div>
                </div>

                <div>
                  <input type='button' value='조회하기' name='search_pw_submit' />
                </div>
              </div>



            {/*  임시 비번 모달   */}
{/* <div id="findPw" 
// className="modal fade"
>
    <div className="modal-dialog modal-dialog-centered modal-login">
        <div className="modal-content">
            <div className="modal-body">

                    <div className="container my-auto">
                        <div className="row">
                            <div className="card z-index-0 fadeIn3 fadeInBottom">
                                <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                    <div className="bg-gradient-primary shadow-primary border-radius-lg py-3 pe-1">
                                        <h4 className="text-white font-weight-bolder text-center mt-2 mb-0">비밀번호 찾기</h4>
                                    </div>
                                </div>
                                <div className="card-body">
                                    <form role="form" className="text-start" action="/member/sendEmail" method="post" name="sendEmail">
                                        <p>입력한 이메일로 임시 비밀번호가 전송됩니다.</p>
                                        <div className="input-group input-group-outline my-3">
                                            <label className="form-label">Email</label>
                                            <input type="email" id="userEmail" name="memberEmail" className="form-control" required />
                                        </div>
                                        <div className="text-center">
                                            <button type="button" className="btn bg-gradient-primary w-100 my-4 mb-2"
                                                    id="checkEmail">비밀번호 발송</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

            </div>
        </div>
    </div>
</div>
    <button type="button" className="btn btn-link" data-bs-toggle="modal"
                                            data-bs-target="#findPw">비밀번호를 잊으셨나요?</button> */}
{/* <script>
    $("#checkEmail").click(function () {
        const userEmail = $("#userEmail").val();
        const sendEmail = document.forms["sendEmail"];
        $.ajax({
            type: 'post',
            url: 'emailDuplication',
            data: {
                'memberEmail': userEmail
            },
            dataType: "text",
            success: function (result) {
                if(result == "no"){
                    // 중복되는 것이 있다면 no == 일치하는 이메일이 있다!
                    alert('임시비밀번호를 전송 했습니다.');
                    sendEmail.submit();
                }else {
                    alert('가입되지 않은 이메일입니다.');
                }

            },error: function () {
                console.log('에러 체크!!')
            }
        })
    });
</script> */}
        </div>
    );
};
}
export default FindPwd;