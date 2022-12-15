import React, { useState, forwardRef, useEffect, useCallback } from 'react';
import "../css/history.css";
import '../css/sidebar.css';
// import Table from 'react-bootsTrap/Table';
import axios from 'axios';
import MoneyHstList from './component/MoneyHstList';
import Paging from './component/Paging';
import moment, { now } from 'moment';
// 선언하지 않아도, 디바이스 혹은 locale의 시간을 불러온다. 
import 'moment/locale/ko';	// 대한민국
import {Table, Thead, Tbody, Tr, Th, Td} from 'react-super-responsive-table';
import 'react-super-responsive-table/dist/SuperResponsiveTableStyle.css';
import { useMemo } from 'react';

// startDate endDate
function History2(props) {

    console.log('History2  랜더링 ~~~~~');
    const today = new Date();
    var nowTime = moment().format('YYYY-MM-DD');
    console.log('nowTime => ', nowTime);
    const [startDate, setStartDate] = useState(""); //yyyy-mm-dd hh:mm:ss
    const [endDate, setEndDate] = useState("");
    const ExampleCustomInput = forwardRef(
        ({ value, onClick }, ref) => (
        <button className="example-custom-input" onClick={onClick}>
        {value}
        </button>
    ));

    const [hstList, setHstList] = useState([]);
    // const [translist, settranslist] = useState([]);  // 리스트에 나타낼 아이템들
    const [count, setCount] = useState(0); // 아이템 총 개수 //총 카운트로 대체
    const [currentPage, setCurrentPage] = useState(1); // 현재 페이지. default 값으로 1
    const [postPerPage,setPostPerPage] = useState(10); // 한 페이지에 보여질 아이템 수 
    const [indexOfLastPost, setIndexOfLastPost] = useState(0); // 현재 페이지의 마지막 아이템 인덱스

    const [searchCurrentPage, setSearchCurrentPage] = useState(1);// 현재 페이지. 인데 조회 경우!!!

    const [payMeanCd, setPayMeanCd] = useState("00");


    const setPage = (e) => {
        setCurrentPage(e)
        if (e == 1 ){
            setIndexOfLastPost(0)
        } else {
            setIndexOfLastPost((e - 1) * 10)
        };
    };
    const setSearPage = (e) => {
        setSearchCurrentPage(e)
        if (e == 1 ){
            setIndexOfLastPost(0)
        } else {
            setIndexOfLastPost((e - 1) * 10)
        };
    };

    // date
    // useEffect(() => {
    //     console.log(`startDate ${startDate} \n ~ endDate ${endDate}`);
    //     console.log('typeof(startDate) => ', typeof(startDate)); 
    // }, [startDate, endDate]);

    const startVal = new Date(startDate);
    const endVal = new Date(endDate);
    // ============ 날짜 선택 시 ============
    const handleStartDate = (e) => {
        let val = e.target.value;
        console.log('val => ',val);
        console.log('typeof(val) => ', typeof(val)); 
        setStartDate(() => val);
        // setStartDate(() => startVal);
    }
    const handleEndDate = (e) => {
        let val = e.target.value;
        console.log('val => ',val);
        console.log('typeof(val) => ', typeof(val)); 
        setEndDate(() => val);
        // setEndDate(() => endVal);

    }

    useEffect(() => { 
        console.log("################### useEffect ####################");
        console.log("전체 useEffect 00000 거래내역 페이지 ============ ");
        let membSn = sessionStorage.getItem("membSn");
        console.log('payMeanCd => ', payMeanCd);
        console.log('membSn => ', membSn);
        let url = global.ipAddress+":8888/member/moneyTransferHst/"+membSn+"/"
        // let url = "http://192.168.35.117:8888/member/moneyTransferHst/"+membSn+"/"
            +indexOfLastPost+"?page="+(currentPage-1) + "&size=" + postPerPage;
            // indexOfLastPost <= 이게 백에서 rownum 으로 파라미터 받음 
        console.log('membSn :',membSn, 'url :',url);
        axios.post(url, 
        {
        }).then((res) => {
            console.log('조인!!! res =>', res)
            // console.log('typeof(res) =>', typeof(res))
            // console.log('typeof res.data => ', typeof(res.data));
            // console.log('조인!!! res.data.content => ', res.data.content);
            setCount(() => res.data.totalElements);
            console.log("currentPage =>", currentPage);
            setHstList(() => res.data.content);
        }).catch((error) => {
            console.log(error);
        })
    },[currentPage]);

    // const currentHistory = 
    useEffect(() => { 
        console.log("################## useEffect ########################");
        if(startDate == "" || endDate == ""){
            console.log("결제수단만 조회 useEffect 11111 조회 거래내역 페이지 *******");
            let membSn = sessionStorage.getItem("membSn");
            console.log('payMeanCd => ', payMeanCd);
            console.log('membSn => ', membSn);
            let url = global.ipAddress+":8888/member/moneyTransferHstByPayMean/"+membSn+"/"
                    +indexOfLastPost+"?page="+(searchCurrentPage-1) + "&size=" + postPerPage+"&payMeanCd="+payMeanCd
                    // +"&startDate="+startDate+"&endDate="+endDate;
                    +"&startDate="+"1000-01-01"+"&endDate="+nowTime; 
                // indexOfLastPost <= 이게 백에서 rownum 으로 파라미터 받음 
            console.log('membSn :',membSn, 'url :',url);
            axios.post(url, 
            {
            }).then((res) => {
                console.log('조인!!! res =>', res)
                // console.log('typeof(res) =>', typeof(res))
                // console.log('typeof res.data => ', typeof(res.data));
                // console.log('조인!!! res.data.content => ', res.data.content);
                setCount(() => res.data.totalElements);
                console.log("searchCurrentPage23131 =>", searchCurrentPage);
                setHstList(() => res.data.content);
            }).catch((error) => {
                console.log(error);
            })
        }else{
            let membSn = sessionStorage.getItem("membSn");
            console.log('거래기간 & 결제수단 조회 useEffect 22222    ~~~~~~~~~~~~~~~~~~~~~');
            console.log(`startDate : ${startDate} , endDate : ${endDate}`);
            // let url = "http://192.168.10.138:8888/member/moneyTransferHstByPayMean/"+membSn+"/"
            let url = global.ipAddress+":8888/member/moneyTransferHstByPayMean/"+membSn+"/"
                +indexOfLastPost+"?page="+(searchCurrentPage-1) + "&size=" + postPerPage+"&payMeanCd="+payMeanCd
                +"&startDate="+startDate+"&endDate="+endDate;
                // +"&startDate="+startDate+"&endDate="+endDate;
                // indexOfLastPost <= 이게 백에서 rownum 으로 파라미터 받음 
            // console.log('membSn :', membSn, 'url :', url);
            axios.post(url)
            .then((res) => {
                console.log('조회 res =>', res);
                setCount(() => res.data.totalElements);
                console.log("searchCurrentPage =>", searchCurrentPage);
                setHstList(() => res.data.content);
            }).catch((error) => {
                console.log(error);
            })
        }
    },[searchCurrentPage]);

    // ============ 결제수단 버튼 클릭 ============ 
    const onClickPayMeanCd = (e) => {
        let meanVal = e.target.value;
        setPayMeanCd(() => meanVal);
        console.log('결제수단 클릭! => ', e.target.value);
        console.log('typeof(meanVal) => ', typeof(meanVal));
    }
    // ============ "조회" 버튼 ver.2 - onSearch ============ startDate endDate ******************
    const onSearch = (e) => {
        console.log("****************** onSearch ******************");
        // setSearchCurrentPage(() => 1);
        // setPostPerPage(() => 10);
        console.log('searchCurrentPage => ', searchCurrentPage);
        if(startDate == "" || endDate == ""){
            let membSn = sessionStorage.getItem("membSn");
            console.log('onSearch 거래기간 & 결제수단 조회 1111111')
            console.log(`startDate : ${startDate} , endDate : ${endDate}`);
            let url = global.ipAddress+":8888/member/moneyTransferHstByPayMean/"+membSn+"/"
                +indexOfLastPost+"?page="+(searchCurrentPage-1) + "&size=" + postPerPage+"&payMeanCd="+payMeanCd
                +"&startDate="+"1000-01-01"+"&endDate="+nowTime; 
                // indexOfLastPost <= 이게 백에서 rownum 으로 파라미터 받음 
            console.log('거래기간 & 결제수단 조회 \n    membSn :', membSn, 'url :', url);
            axios.post(url)
            .then((res) => {
                console.log('조회 res =>', res);
                setCount(() => res.data.totalElements);
                console.log("searchCurrentPage =>", searchCurrentPage);
                setHstList(() => res.data.content);
            }).catch((error) => {
                console.log(error);
            })
        }else{
            let membSn = sessionStorage.getItem("membSn");
            console.log('onSearch 거래기간 & 결제수단 조회 222222222')
            console.log(`startDate : ${startDate} , endDate : ${endDate}`);
            let url = global.ipAddress+":8888/member/moneyTransferHstByPayMean/"+membSn+"/"
                +indexOfLastPost+"?page="+(searchCurrentPage-1) + "&size=" + postPerPage+"&payMeanCd="+payMeanCd
                +"&startDate="+startDate+"&endDate="+endDate;
            console.log('거래기간 & 결제수단 조회 \n    membSn :', membSn, 'url :', url);
            axios.post(url)
            .then((res) => {
                console.log('조회 res =>', res);
                setCount(() => res.data.totalElements);
                console.log("searchCurrentPage =>", searchCurrentPage);
                setHstList(() => res.data.content);
            }).catch((error) => {
                console.log(error);
            })
        }
    }


//////////////////////////////////////////////////////////////////////////////////////////////////
    return (
        <>
        <div className="history_wrap">
        <Table >
            <Tbody>
            <Tr>
                <Td>거래기간</Td>
                <Td>
                    <input type='date' name='startDate' id='startDate'
                        onChange={handleStartDate } value={startDate} // value={moment(This).format("YYYY-MM-DD")}
                    />
                </Td>
                <Td>~</Td>
                <Td>
                    <input type='date' name='endDate' id='endDate'
                        onChange={handleEndDate } value={endDate} 
                        min={startDate} 
                    />
                </Td>
                <Td >결제수단</Td>
                <Td>
                    <select defaultValue={"00"} onChange={onClickPayMeanCd}>
                        <option value="00">모든 결제수단</option>
                        <option value="03">자체머니결제</option>
                        <option value="01">카드</option>
                        <option value="02">계좌이체</option>
                    </select>
                </Td>
                <Td>
                    {/* ===== 버튼 ===== */}
                    <button onClick={onSearch} type='button'
                        className='btn btn-secondary' >
                        조회
                    </button>
                </Td>
            </Tr>
            </Tbody>
        </Table>

        <div >
        <Table >
            <Thead>
            <Tr className="target_tr">
                <Th>일자</Th>
                <Th>처리구분</Th>
                <Th>결제수단</Th>
                <Th>상품명</Th>
                <Th>가맹점명</Th>
                <Th>처리금액</Th>
                <Th>처리상태</Th>
            </Tr>
            </Thead>
            <Tbody>
                <MoneyHstList hstList={hstList}/>
            </Tbody>
        </Table>
        {/* <CallPage/> */}
        {
            payMeanCd =="00" && startDate == ""
            ? <Paging page={currentPage} count={count} setPage={setPage} 
                indexOfLastPost={indexOfLastPost} />
            :<Paging page={searchCurrentPage} count={count} setPage={setSearPage} 
                indexOfLastPost={indexOfLastPost} />
        }
        </div>
        </div>
        </>

    );

}

export default History2;