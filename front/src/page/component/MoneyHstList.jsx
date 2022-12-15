import axios from "axios";
import React, {useEffect, useState} from "react";
import {Table, Thead, Tbody, Tr, Th, Td} from 'react-super-responsive-table';
import 'react-super-responsive-table/dist/SuperResponsiveTableStyle.css';

const MoneyHstList = ({hstList}) => {

    
    // const [count, setCount] = useState(0); // 아이템 총 개수 //총 카운트로 대체
    // const [currentPage, setCurrentPage] = useState(1); // 현재 페이지. default 값으로 1
    // const [postPerPage,setpostPerPage] = useState(5); // 한 페이지에 보여질 아이템 수 
    // const [indexOfLastPost, setIndexOfLastPost] = useState(0); // 현재 페이지의 마지막 아이템 인덱스


    return (
        <>
        {
            hstList.map((hst, idx) => (
                <Tr key={idx}>
                <Td>{(hst.frstRegistDt).substring(0,10)}</Td>
                {
                    hst.transferTyCd == "01" ?
                    <Td>충전</Td>
                    : hst.transferTyCd == "02" ?
                    <Td>결제</Td>
                    : <Td></Td>
                    // : null
                }
                {
                    hst.payMeanCd == "01" ?
                    <Td>카드</Td>
                    : hst.payMeanCd == "03" ?
                    <Td>머니사용</Td>
                    : <Td></Td>
                }
                <Td>{hst.goodsNm}</Td>
                <Td>{hst.merchantNm}</Td>
                <Td>{hst.transferAmt} 원</Td>
                {
                    hst.moneyTransferHstSn != null ?
                    <Td>정상</Td>
                    : <Td>오류</Td>
                }
                </Tr>
        ))} 
         </>
    );

};

export default MoneyHstList;