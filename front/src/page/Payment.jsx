import React, { useEffect } from 'react';
import Table from 'react-bootstrap/Table';
import Sidebar from "./Sidebar";
import { Button   } from 'react-bootstrap';
import axios from 'axios';
import { useState } from 'react';
import {  useNavigate } from 'react-router-dom';
import { Field, Formik, Form } from "formik";

const Payment = (effect, deps) => {
    const navigate = useNavigate();
    // const [merchantSn, setMerchantSn] = useState();
    const [mercList, setMercList] = useState();
    const [goodsList, setGoodsList] = useState();
    const [goodsAmt, setGoodsAmt] = useState(0);
    const [goodsNo, setGoodsNo] = useState("");
    const [goodsNm, setGoodsNm] = useState("");
    const [money, setMoney] = useState("");
    const [payMeanCd, setPayMeanCd] = useState("03"); // 결제수단코드 
    
    const onMoneyHandler = (event) => {
        setMoney(event.currentTarget.value);
    }
    
    // ================ 결제 페이지로 올 때 "가맹점" 정보 가져오기 ================
    useEffect(() => {
        console.log("============ 결제 페이지 ============ ");
        // axios.get("http://192.168.10.138:8888/member/merchants", 
        axios.get("http://192.168.35.117:8888/member/merchants", 
        {
        }).then((res) => {
            // console.log('typeof(res) =>', typeof(res))
            // console.log('res =>', res)
            // console.log('typeof(res.data) =>', typeof(res.data))
            // console.log('res.data =>', res.data)
            setMercList(res.data);
            // setMerchantSn(res.data.merchantSn);
            // console.log('typeof mercList =>', typeof(mercList))
            // console.log('mercList =>', mercList)
        }).catch((error) => {
            console.log(error);
        })
    }, []);

    // ================ 가맹점 선택하면 그 "가맹점"의 "상품" 정보 가져오기 ================
    const onClickMerchant = (e) => {
        console.log('가맹점 클릭 !!!!!');
        let merchantSn = e.target.value;
        console.log('typeof merchantSn =>', typeof(merchantSn));

        // axios.get("http://192.168.10.138:8888/member/merchants/"+merchantSn, 
        axios.get("http://192.168.35.117:8888/member/merchants/"+merchantSn, 
        {
        }).then((res) => {
            // console.log('typeof(res) =>', typeof(res))
            // console.log('res =>', res)
            // console.log('typeof(res.data) =>', typeof(res.data))
            // console.log('res.data =>', res.data)
            setGoodsList(res.data);
            // console.log('typeof goodsList =>', typeof(goodsList))
            console.log('goodsList =>', goodsList)
        }).catch((error) => {
            console.log(error);
        })
    }
    // onClickGood
    const onClickGood = (e) => {
        console.log('구매물품 클릭 !!!!!');
        setGoodsNo(e.target.value);
        let goodsNo = e.target.value;
        console.log('typeof goodsNo =>', typeof(goodsNo));
        // axios.get("http://192.168.10.138:8888/member/goods/"+goodsNo, 
        axios.get("http://192.168.35.117:8888/member/goods/"+goodsNo, 
        {
        }).then((res) => {
            // console.log('typeof(res) =>', typeof(res))
            // console.log('res =>', res)
            // console.log('typeof(res.data) =>', typeof(res.data))
            console.log('res.data =>', res.data)
            setGoodsAmt(res.data.goodsAmt);
            setGoodsNm(res.data.goodsNm);
            // console.log('typeof goodsAmt =>', typeof(goodsAmt))
            // console.log('goodsAmt =>', goodsAmt)
            console.log('=========================')
        }).catch((error) => {
            console.log(error);
        })
    }
    // useEffect(() => {
    //     console.log('goodsAmt =>', goodsAmt)
    // }, [goodsAmt]);

    let payMeanCdHandler = (e) => {
        setPayMeanCd(e.target.value);
    }
    
    // ================ "결제하기" 클릭 시 수단에 따라 실행되도록 ================
    const onPay = (e) => {
        console.log('payMeanCd vvv => ', payMeanCd);
        if(payMeanCd == "03"){ // 선불머니 결제 - 미리 충전해놓은 것
            console.log('머니 선택 - 03');
            onPayMoney();
        } else if (payMeanCd == "01") { // 카드 결제 - 카카오페이로 직접 결제 
            console.log('카드 선택 - 01');
            onPayCard();
        } else if (payMeanCd == "02") { // 계좌이체 결제 
            console.log('계좌이체 선택 - 02');
        }
    }

    // --------------- (1) 결제 - 선불머니 :: 결제수단코드 03 --------------- payMoney
    const onPayMoney = (event) => {
        // event.preventDefault();
        console.log('선불머니로 결제하자!');
        let data = {
            member : {
                membSn : sessionStorage.getItem('membSn') // 회원번호
            },
            goods : {
                goodsNo : goodsNo   // 상품번호 
            },
            goodsAmt: goodsAmt,     // 결제금액
            // transferTyCd: '02',// 거래종류코드 (01:충전, 02:사용, 03:환전) <= 나중에 코드까지 buyHst에서 받아서 메서드 합쳐서 간결하게 만들어도 좋을 듯? 결제수단에 따른 if else...
        }
        // axios.post("http://192.168.10.138:8888/member/payMoney", data, {
        axios.post("http://192.168.35.117:8888/member/payMoney", data, {
        headers: {
            "Content-Type": "application/json",
        }
        }).then(res => {
            // console.log('typeof(res) =>', typeof(res));
            console.log('res.data => ',res.data);
            window.confirm('결제 성공하였습니다.');
            navigate("/history");
        }).catch((error) => {
            console.log(error);
        })
    }

    // --------------- (2) 결제 - 카드 :: 결제수단코드 01 --------------- payCard >>> 아임포트 - 카카오페이 ================
    const { IMP } = window;
    IMP.init('imp08030724'); // 결제 데이터 정의

    // 가맹점 식별하기
    const onPayCard = (e) => {
        console.log('다날 로 결제하자!');
        const data = {
            pg: 'danal_tpay',           // PG사 (필수항목)
            pay_method: 'card',           // 결제수단 (필수항목)
            merchant_uid: `mid_${new Date().getTime()}`, // 결제금액 (필수항목)
            name: goodsNm,           // 주문명 (필수항목)
            amount: goodsAmt,               // 금액 (필수항목)
            custom_data: { name: '부가정보', desc: '세부 부가정보' },
            buyer_name: sessionStorage.getItem('membId'),          // 구매자 이름
            buyer_tel: '01012341234',       // 구매자 전화번호 (필수항목)
            buyer_email: 'alfmsp123@naver.com',// 구매자 이메일
            buyer_addr: '서울',           // 주소
            buyer_postalcode: 12345,
            // m_redirect_url : 'http://192.168.10.138:3000/member/payCard'
        };
        IMP.request_pay(data, callback);    
        // 원래 안드로이드 웹뷰에선 callback 실행 안되는데 다날로 하면 됨!
    }// >>>>>>> 콜백 
    const callback = (rsp) => {
        const {success, error_msg, imp_uid, merchant_uid, pay_method, paid_amount, status} = rsp;
        if (success) {
            // axios로 HTTP 요청
            axios({
                // url: "http://192.168.10.138:8888/member/payCard",
                url: "http://192.168.35.117:8888/member/payCard",
                method: "post",
                headers: { "Content-Type": "application/json" },
                data: {
                    member : {membSn : sessionStorage.getItem('membSn') },
                    goods : {goodsNo : goodsNo },
                    goodsAmt: goodsAmt,
                }
            }).then((rsp) => {
                console.log('결제 rsp =>', rsp.data);
                window.confirm('결제 성공하였습니다.');
                navigate("/history");
            }).catch((error) => {
                console.log(error);
            })
        } else {
            alert(`결제 실패하였습니다. : ${error_msg}`);
        }
    }




//////////////////////////////////////////////////////////////////////////////////////////////////
    return (
        <>
        <div className="charge_box">
            <div className="charge_sidebar">
                <Sidebar></Sidebar>
            </div>
            <div className="charge_container">
                <div className="charge_wrap">
                {/* <form onSubmit={onPay}> */}
                <Formik 
                initialValues={{
                    name: ''
                }}
                onSubmit={onPay}
                >
                <Form>
                <Table striped>
                    <thead>
                    <tr>
                        <th></th>
                        <th>내용</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>가맹점</td>
                        <td>
                            <select onChange={onClickMerchant} >
                                <option value="">== 선택 ==</option>
                                {/* {mercList != undefined && mercList.map((merc, idx) => ( */}
                                {mercList && mercList.map((merc, idx) => (
                                    <option key={idx} value={merc.merchantSn} > 
                                        {merc.merchantNm}</option>
                                // JavaScript에서 true && expression은 항상 expression으로 실행되고 
                                // false && expression은 항상 false로 실행된다. 
                                // 따라서 조건이 참이면 && 바로 뒤의 요소가 출력에 나타난다. 
                                // 거짓이면 React는 무시하고 건너뛴다. 
                                ))}
                            </select>
                        </td>

                    </tr>
                    <tr>
                        <td>구매물품</td>
                        <td>
                            <select onChange={onClickGood}>
                                <option value="">== 선택 ==</option>
                                {goodsList && goodsList.map((good, idx) => (
                                    <option key={idx} value={good.goodsNo} > 
                                        {good.goodsNm}</option>
                                ))}
                            </select>
                        </td>

                    </tr>
                    <tr>
                        <td>결제금액</td>
                            {/* {
                                goodInfo == NaN
                                ? setGoodsAmt(0)
                                : <td>{goodInfo} 원 </td>
                            } */}
                            <td>{goodsAmt} 원 </td>
                            {/* <input value={goodInfo} onChange={onMoneyHandler} readOnly
                            type="Integer" className="charge" style={{border:"none"}}/> 원 */}
                        
                    </tr>
                    <tr>
                        <td>결제수단</td>
                        <td>
                            <select onChange={payMeanCdHandler}> {/* 선택한거 payMeanCd 변수에 저장해서 결제 시 데이터로 같이 넘기기  */}
                                <option value="03">선불머니</option>
                                <option value="01">카드</option>
                                <option value="02">계좌이체</option>
                            </select>
                        </td>
                    </tr>
                    </tbody>
                </Table>
                <div className='form-row float-right'>
                    {/* <Button variant="primary" type="submit" className='payBtn' >
                        결제하기
                    </Button> */}
                    {/* 미르 테스트 - 결제하기 버튼 */}
                    <Button variant="primary" type="submit" className='payBtn' >
                        결제하기
                    </Button>
                </div>
                {/* </form> */}
                </Form>
            </Formik>
                </div>
            </div>
        </div>
        </>
);
}

export default Payment;