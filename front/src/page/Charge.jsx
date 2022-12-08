import React, { useEffect, useLayoutEffect, useState, useNavigate } from 'react';
import "../css/charge.css";
import Table from 'react-bootstrap/Table';
import Sidebar from "./Sidebar";
import '../css/sidebar.css';
import "bootstrap/dist/css/bootstrap.min.css";
import { Button   } from 'react-bootstrap';
import axios from 'axios';
import SidebarMR from './SidebarMR';
import SidebarMR2 from './SidebarMR2';


function Charge(props) {
    // const navigate = useNavigate();
    const [memb, setMemb] = useState([]);
    const [money, setMoney] = useState("");     // 머니 "충전"액
    const [expMoney, setExpMoney] = useState(); // 충전"예정"결과액 
    const [transSn, setTransSn] = useState(0);  // 충전 후 거래이력"번호"
    
    const onMoneyHandler = (event) => {
        setMoney(event.currentTarget.value);
        /* console.log('typeof(money) => ', typeof(money));
        console.log('typeof(parseInt(money)) => ', typeof(parseInt(money))); */
    }
    // const expMoneyHandler = (e) => {
    //     setExpMoney(e.value + expMoney);
    // }

    useEffect(() => {
        setExpMoney(memb.moneyBlce + parseInt(money));
    }, [money]);

    // ================ 충전 페이지로 올 때 로그인한 회원의 머니 정보 가져오기 ================
    useEffect(() => { // useEffect(함수, 배열) : 컴포넌트가 화면에 나타났을 때 자동 실행
        console.log("============ 머니충전 페이지 ============ ");
        let membSn = sessionStorage.getItem("membSn");
        console.log('typeof(membSn) => ', typeof(membSn));
        // 수행할 함수 
        // axios.get("http://192.168.10.138:8888/member/money/"+membSn, 
        axios.get(global.ipAddress+":8888/member/money/"+membSn, 
        {
        }).then((res) => {
            // 서버 결제 API 성공시 로직
            setMemb(res.data);
            setExpMoney(res.data.moneyBlce);
            console.log('res.data => ', res.data);
            console.log('typeof res.data => ', typeof(res.data));
            // console.log('typeof res.data.moneyBlce => ', typeof(res.data.moneyBlce));
            console.log('transSn => ',transSn);
            console.log('=========== useEffect ============');
        })
        // return () => { // cleanup함수 : useEffect 함수가 다시 실행될 때(실행되기 직전), 
        //               //                return 함수를 먼저 실행해 주고 넘어가는것?
        // }
    }, [transSn]); 
    // 빈 배열을 입력할 경우 컴포넌트가 Mount 될 때에만 실행 - jquery에서 $( document ).ready(function() { } 와 유사
    // 배열 안에 값을 넣어주면 그 값이 변경될 때마다 실행 



    // ================ 아임포트 - 카카오페이 ================
    const { IMP } = window;
    IMP.init('imp08030724'); // 결제 데이터 정의

    // 가맹점 식별하기
    const onClickCharge = (e) => {
        e.preventDefault();
        console.log('money => ' + money);

        const data = {
            pg: 'danal_tpay',           // PG사 (필수항목)
            pay_method: 'card',           // 결제수단 (필수항목)
            merchant_uid: `mid_${new Date().getTime()}`, // 
            name: 'E4. 머니 충전',           // 주문명 (필수항목)
            amount: money,               // 금액 (필수항목)
            custom_data: { name: '부가정보', desc: '세부 부가정보' },
            buyer_name: sessionStorage.getItem('membId'),          // 구매자 이름
            buyer_tel: '01012341234',       // 구매자 전화번호 (필수항목)
            buyer_email: 'alfmsp123@naver.com',// 구매자 이메일
            buyer_addr: '서울',           // 주소
            buyer_postalcode: 12345
        };
        // setMerchantUid(data.merchant_uid);
        // console.log('data.merchant_uid => ' + data.merchant_uid);
        IMP.request_pay(data, callback);
        // navigate("/history");
    }

    // 콜백 
    const callback = (rsp) => {
        console.log('callback!!!!!');
        let {success, error_msg, imp_uid, pay_method, paid_amount, status} = rsp;
        console.log('rsp =>', rsp);
        console.log('rsp.success =>', rsp.success);
        console.log('imp_uid =>', imp_uid);
        console.log('membSn => ',sessionStorage.getItem('membSn'));
        // let body = {
        //     transferTyCd: '1',      // 거래종류코드 (01:충전, 02:사용, 03:환전)
        //     transferAmt: money,     // 충전금액
        //     payTranserNo: imp_uid,  // 결제거래번호
        //     member : {
        //         membSn : sessionStorage.getItem('membSn') // 회원번호
        //     }
        // }
        // let data = JSON.stringify(body);
        if (rsp.success) {
            axios({
                url: global.ipAddress+":8888/member/charge",
                // url: "http://192.168.35.117:8888/member/charge",
                method: "post",
                method: "post",
                headers: { "Content-Type": "application/json" },
                data: JSON.stringify({
                    transferTyCd: '01',     // 거래종류코드 (01:충전, 02:사용, 03:환전)
                    transferAmt: money,     // 충전금액
                    payTranserNo: imp_uid,  // 결제거래번호
                    member : {
                        membSn : sessionStorage.getItem('membSn') // 회원번호
                    }
                })
            })
            .then((res) => {
                // 서버 결제 API 성공시 로직
                // alert('머니 충전 성공하였습니다.');
                var returnVal = window.confirm('머니 충전 성공하였습니다.');
                // corfirm 으로 바꿔서 해보기 !!!!!!!
                console.log('typeof(res) => ', typeof(res));
                console.log('res => ', res);
                console.log('res.data => ', res.data);
                if(returnVal) {
                    setTransSn(res.data.moneyTransferHstSn);
                    console.log('확인 누르면 거래이력번호 생김 \n '+
                        'res.data.moneyTransferHstSn => ', res.data.moneyTransferHstSn);
                    setMoney("");
                    console.log('머니충전액 입력창 => ', money);
                    // moneyUpdate();
                    console.log('=========== 머니 충전 성공하였습니다 ============');
                }
            })
        } else {
            alert(`머니 충전 실패하였습니다. : ${error_msg}`);
        }
    }


//////////////////////////////////////////////////////////////////////////////////////////////////
    return (
        <div className="charge_box">
            <div className="charge_sidebar">
            <Sidebar></Sidebar>
            {/* <sidebar></sidebar> */}
            {/* <SidebarMR></SidebarMR> */}
            {/* <SidebarMR2></SidebarMR2> */}
            {/* <nav class="navbar navbar-default"></nav> */}
            </div>
        <div className="charge_container">
            <div className="charge_wrap">
            <Table striped>
                <thead>
                <tr>
                    <th></th>
                    <th>금액</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>머니잔액</td>
                    <td>
                        <div>{memb.moneyBlce} (원)</div>
                    </td>
                </tr>
                <tr>
                    <td>머니충전액</td>
                    <td>
                        <input value={money} onChange={onMoneyHandler}
                            type='number' className="charge"/>원
                    </td>
                </tr>
                <tr>
                    <td>충전결과예정액</td>
                    {/* <td onChange={expMoneyHandler}>{expMoney} (원)</td> */}
                    
                    {
                        expMoney === NaN
                        ? setExpMoney(0)
                        : <td>{expMoney} (원)</td>
                    }
                    {/* {expMoney}  */}
                </tr>
                </tbody>
            </Table>
            <div className='form-row float-right'>
            <Button onClick={onClickCharge} variant="primary" type="submit" className='' >
                충전하기
            </Button>
            </div>
            </div>
        </div>
    </div>
    );

}

export default Charge;