import React, { useState, useEffect } from "react";
import Pagination from "react-js-pagination";
import "../../css/paging.css";


// const Paging = () => {
const Paging = ({page, count, setPage}) => {
    // const [page, setPage] = useState(1);
    // const handlePageChange = (page) => {
    //     setPage(page);
    //     console.log('page => ', page);
    // };
    useEffect(() => {
        console.log('page :',page, 'count :',count);
    },[page])
    return (
        <Pagination
        
            activePage={page}       // 현재 페이지
            itemsCountPerPage={10}   // 한 페이지 당 보여줄 리스트 아이템 개수
            totalItemsCount={count} // 총 아이템 개수
            pageRangeDisplayed={5}  // Paginator 내에서 보여줄 페이지의 범위
            prevPageText={"‹"}      // "이전"을 나타낼 텍스트 (prev, <, ...)
            nextPageText={"›"}      // "다음"을 나타낼 텍스트 (next, >, ...)
            onChange={setPage}      // 페이지 변경을 핸들링하는 함수
        />
    );
};

export default Paging;