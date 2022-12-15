import React, { memo } from 'react';
import { Route, Routes } from 'react-router-dom';
import Charge from './page/Charge';
import History from './page/History';
import Join2 from './page/Join2';
import Login from './page/Login';
import Payment from './page/Payment';
import MainPage from './page/MainPage';
import MainPage3 from './page/MainPage3';
import MyPage from './page/MyPage';

const AppRoutes = () => {
  return (
    <Routes>
        {/* <Route path="/" element={<Home />}/> */}
        <Route path="/" element={<Login />}/>
        <Route path="/join" element={<Join2 />}/>
        <Route path='/main' element={<MainPage /> }>
            <Route path="charge" element={<Charge/>}/>
            <Route path="payment" element={<Payment/>}/>
            <Route path="history" element={<History />}/>
            {/* My Page 추가  */}
            <Route path='mypage' element={<MyPage/> } />
        </Route>

        {/* 테스트 용 */}
        <Route path="/main3" element={<MainPage3 />} />
    </Routes>
  );
};

export default memo(AppRoutes);