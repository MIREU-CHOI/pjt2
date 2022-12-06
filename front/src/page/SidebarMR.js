import React, { useCallback, useEffect, useState } from 'react';

function SidebarMR(props) {
    return (
    <div className="container-fluid" style={{height:"100%" }}>
        <div className="row justify-content-around"
            style={{height:"100%" }}></div>
        <div className="col-2 hcategory ">
            <div
                className="d-flex flex-column align-items-start justify-content-center ps-5"
                style={{height:"160px"}}>
                <h5 className="hanna text-white">test 님</h5>
                <span className="nanum text-white" style={{fontSize: "12px" }}>
                    일반회원 ・ <a style={{textDecoration:"none"}} >마이페이지</a>
                </span>
            </div>
            <div>
                <ul className="nav flex-column">
                    <li className="nav-item pt-2 pb-2 ps-4"><a
                        className="nav-link text-white">예약목록
                    </a></li>
                    <li className="nav-item pt-2 pb-2 ps-4"><a
                        className="nav-link text-white">처방전
                    </a></li>
                    <li className="nav-item pt-2 pb-2 ps-4"><a
                        className="nav-link text-white">문진표
                    </a></li>
                </ul>
            </div>
		</div>


    </div>
    );
};

export default SidebarMR;