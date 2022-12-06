import React, { useState, forwardRef, useEffect } from 'react';
// import "../../Charge/css/charge.css";
import "../css/history.css";
import '../css/sidebar.css';
import Table from 'react-bootstrap/Table';
import Sidebar from "./Sidebar";
import DatePicker from "react-datepicker";
import { ko } from 'date-fns/esm/locale';
import axios from 'axios';
import MoneyHstList from './component/MoneyHstList';
import Paging from './component/Paging';


function History(props) {
    console.log("거래내역 페이지입니다.");
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
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
    const [postPerPage,setpostPerPage] = useState(10); // 한 페이지에 보여질 아이템 수 
    const [indexOfLastPost, setIndexOfLastPost] = useState(0); // 현재 페이지의 마지막 아이템 인덱스

    const [payMeanCd, setPayMeanCd] = useState("");


    const setPage = (e) => {
        setCurrentPage(e)
        if (e == 1 ){
            setIndexOfLastPost(0)
        } else {
            setIndexOfLastPost((e - 1) * 10)
        };
    };

    
    useEffect(() => { 
        let membSn = sessionStorage.getItem("membSn");
        let url = "http://192.168.10.138:8888/member/moneyTransferHst/"+membSn+"/"
            +indexOfLastPost+"?page="+(currentPage-1) + "&size=" + postPerPage;
            // indexOfLastPost <= 이게 백에서 rownum 으로 파라미터 받음 
        console.log('membSn :',membSn, 'url :',url);
        axios.post(url, 
        {
        }).then((res) => {
            console.log('typeof(res) =>', typeof(res))
            console.log('조인!!! res =>', res)
            console.log('typeof res.data => ', typeof(res.data));
            console.log('조인!!! res.data.content => ', res.data.content);
            setCount(res.data.totalElements);
            console.log("currentPage =>", currentPage);
            setHstList(res.data.content);
        }).catch((error) => {
            console.log(error);
        })
    }, [currentPage]); 

    // onClickPayMeanCd
    const onClickPayMeanCd = (e) => {
        setPayMeanCd(e.target.value);
        console.log('결제수단 클릭! => ', e.target.value);
    }
    const onPayMeanCdSearch = (e) => {
        let membSn = sessionStorage.getItem("membSn");
        let url = "http://192.168.10.138:8888/member/moneyTransferHstByPayMean/"+membSn+"/"
            +indexOfLastPost+"?page="+(currentPage-1) + "&size=" + postPerPage+
            "&payMeanCd="+payMeanCd;
            // indexOfLastPost <= 이게 백에서 rownum 으로 파라미터 받음 
        console.log('membSn :',membSn, 'url :',url);
        axios.post(url, 
        {
        }).then((res) => {
            console.log('typeof(res) =>', typeof(res))
            console.log('조인!!! res =>', res)
            console.log('typeof res.data => ', typeof(res.data));
            console.log('조인!!! res.data.content => ', res.data.content);
            setCount(res.data.totalElements);
            console.log("currentPage =>", currentPage);
            setHstList(res.data.content);
        }).catch((error) => {
            console.log(error);
        })
    }
    


//////////////////////////////////////////////////////////////////////////////////////////////////
    return (
    <div className="history_box">
        <div className="history_sidebar">
            <Sidebar></Sidebar>
        </div>
    <div className="history_container">
        <div className="history_wrap">
        <form onSubmit={onPayMeanCdSearch}>
        <Table className="table table-borderless table-sm">
            <tbody>
            <tr>
                <td>거래기간</td>
                <td><DatePicker 
                    selected={startDate}
                    onChange={date => setStartDate(date)}
                    customInput={<ExampleCustomInput />}
                    locale={ko}
                    dateFormat="yyyy년 MM월 dd일"
                    // selectsStart
                    startDate={startDate}
                    endDate={endDate}
                    minDate={new Date()}
                    />
                </td>
                <td>~</td>
                <td><DatePicker 
                    selected={endDate}
                    onChange={date => setEndDate(date)}
                    customInput={<ExampleCustomInput />}
                    locale={ko}
                    dateFormat="yyyy년 MM월 dd일"
                    // selectsEnd
                    endDate={endDate}
                    startDate={startDate}
                    minDate={startDate}
                    />
                </td>
                <td >결제수단</td>
                <td>
                    <select defaultValue={"03"} onChange={onClickPayMeanCd}>
                        <option value="03">자체머니결제</option>
                        <option value="01">카드</option>
                        <option value="02">계좌이체</option>
                    </select>
                </td>
                <td><button className='btn btn-secondary' type='submit'>조회</button></td>
            </tr>
            </tbody>
        </Table>

        <div className='table-responsive'>
            <table className='table table-striped'>
                <thead>
                <tr>
                    <th>일자</th>
                    <th>처리구분</th>
                    <th>결제수단</th>
                    <th>상품명</th>
                    <th>가맹점명</th>
                    <th>처리금액</th>
                    <th>처리상태</th>
                </tr>
                </thead>
                <tbody>
                    <MoneyHstList hstList={hstList}/>
                </tbody>
            </table>

            <Paging page={currentPage} count={count} setPage={setPage} 
                indexOfLastPost={indexOfLastPost} />
            

        </div>
        </form>
        
        </div>
    </div>
    </div>

    );

}

export default History;