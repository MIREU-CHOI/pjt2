import './App.css';
import Join2 from "./page/Join2";
import Login from "./page/Login";
import {Route, Routes} from 'react-router-dom';
import { Navbar , Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './css/home.css'
import Home from './page/Home';
import Charge from './page/Charge';
import Pay from './page/Pay';
import History from './page/History';
import Example from './Example';
import List from './page/List';
import Payment from './page/Payment';
import FindPwd from './page/FindPwd';
import FindPwd2 from './page/FindPwd2';
import AppRoutes from './AppRoutes';


function App() {
  let ipAddress = "http://192.168.10.138"; // company
  // let ipAddress = "http://192.168.35.117";  
  global.ipAddress = ipAddress;
  // let appToken = "dZIDu3WPRsiU4Ec3fzU2xg:APA91bHn5jR_AIOb5GhfaOb3bdZBYn_QT4aoOxtb65ltzYj8hNi7hc36_CKDBFMk-EwKQFOQ7eC_0R33cWHNnfUnCGe1jUBIC2iHFo4YvLDXUdt7KZNLjlAg98RdOXxNOfaqOg5GqgSN";
  let appToken = "ef8gFnFITNulcJ_CEDuD-u:APA91bGdn8CH-tD-NxswVZYNmVecY-etMAIToAank7l875N9uHxQhsds8ME3XeMqGZlhS41Bz_svJTc-h_ye6KbwgMU2tIoD_KQwkiUQmNv4L3-fiVJBhZtZagzYdV-QJoONJaNDg3k0";
  global.appToken = appToken;

  return (
    <div className="App">

      <AppRoutes />

      {/* <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/join" element={<Join2/>}/>
        <Route path="/charge" element={<Charge/>}/>
        <Route path="/pay" element={<Pay/>}/>
        <Route path="/history" element={<History/>}/>
        <Route path="/list" element={<List />}/>
        <Route path="/payment" element={<Payment />}/>
        <Route path="/findPwd" element={<FindPwd />}/>
        <Route path="/FindPwd2" element={<FindPwd2 />}/>

      </Routes> */}
      {/* <Login /> */}
      {/* <Example></Example> */}
      <>
        {/* <Button variant="primary">Primary</Button>{' '}
        <Button variant="secondary">Secondary</Button>{' '}
        <Button variant="success">Success</Button>{' '}
        <Button variant="warning">Warning</Button>{' '}
        <Button variant="danger">Danger</Button> <Button variant="info">Info</Button>{' '}
        <Button variant="light">Light</Button> <Button variant="dark">Dark</Button>{' '}
        <Button variant="link">Link</Button> */}
      </>
    </div>
  );
}

export default App;
