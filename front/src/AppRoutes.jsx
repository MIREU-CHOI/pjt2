import React, { memo } from 'react';
import { Route, Routes } from 'react-router-dom';
import Charge from './page/Charge';
import History from './page/History';
import Join2 from './page/Join2';
import Login from './page/Login';
import MainPage from './page/MainPage';
import Payment from './page/Payment';

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
        </Route>
    </Routes>
  );
};

export default memo(AppRoutes);