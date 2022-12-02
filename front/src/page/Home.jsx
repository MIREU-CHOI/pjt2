import React from 'react';
import Login from "./Login";
import '../css/home.css'

function Home(props) {
    return (
        <div className="home_container">
            {/* <div className="home_info">
                <h1>E4. Pay Service</h1>
                <div>서비스 이용을 위해서는 로그인이 필요합니다.</div>
            </div> */}
            <Login/>
            

        </div>
    );
}

export default Home;