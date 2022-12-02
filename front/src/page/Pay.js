import React from 'react';
import "../css/charge.css";
import Table from 'react-bootstrap/Table';
import Sidebar from "./Sidebar";
import '../css/sidebar.css';
import "bootstrap/dist/css/bootstrap.min.css";
import { Button   } from 'react-bootstrap';


function Pay(props) {

        return (
            <div className="charge_box">
            <div className="charge_sidebar">
            <Sidebar></Sidebar>
            </div>
            <div className="charge_container">
            <div className="charge_wrap">

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
                        <select>
                            <option value="">== 선택 ==</option>
                        </select>
                    </td>

                </tr>
                <tr>
                    <td>구매물품</td>
                    <td>
                        <select>
                            <option value="">== 선택 ==</option>
                        </select>
                    </td>

                </tr>
                <tr>
                    <td>결제금액</td>
                    <td>
                        <input type="Integer" className="charge" style={{border:"none"}}/> 원
                    </td>
                </tr>
                <tr>
                    <td>결제수단</td>
                    <td>
                        <select>
                            <option value="">선불머니</option>
                            <option value="">카트</option>
                            <option value="">계좌이체</option>
                        </select>
                    </td>
                </tr>
                </tbody>
            </Table>
            <div className='form-row float-right'>
            <Button variant="primary" type="submit" className='payBtn' >
                결제하기
            </Button>
            </div>
            </div>
    </div>

</div>

        );

}

export default Pay;