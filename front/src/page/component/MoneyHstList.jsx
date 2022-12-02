import axios from "axios";
import React, {useEffect, useState} from "react";

const MoneyHstList = ({hstList}) => {

    
    // const [count, setCount] = useState(0); // 아이템 총 개수 //총 카운트로 대체
    // const [currentPage, setCurrentPage] = useState(1); // 현재 페이지. default 값으로 1
    // const [postPerPage,setpostPerPage] = useState(5); // 한 페이지에 보여질 아이템 수 
    // const [indexOfLastPost, setIndexOfLastPost] = useState(0); // 현재 페이지의 마지막 아이템 인덱스


    return (
        <>
        {
            hstList.map((hst, idx) => (
                <tr key={idx}>
                <td>{(hst.frstRegistDt).substring(0,10)}</td>
                {
                    hst.transferTyCd == "01" ?
                    <td>충전</td>
                    : hst.transferTyCd == "02" ?
                    <td>결제</td>
                    : <td></td>
                    // : null
                }
                {
                    hst.payMeanCd == "01" ?
                    <td>카드</td>
                    : hst.payMeanCd == "03" ?
                    <td>머니사용</td>
                    : <td></td>
                }
                <td>{hst.goodsNm}</td>
                <td>{hst.merchantNm}</td>
                <td>{hst.transferAmt} 원</td>
                {
                    hst.moneyTransferHstSn != null ?
                    <td>정상</td>
                    : <td>오류</td>
                }
                </tr>
        ))} 
         </>
    );

};

export default MoneyHstList;