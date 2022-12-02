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


function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/join" element={<Join2/>}/>
        <Route path="/charge" element={<Charge/>}/>
        <Route path="/pay" element={<Pay/>}/>
        <Route path="/history" element={<History/>}/>
        <Route path="/list" element={<List />}/>
        <Route path="/payment" element={<Payment />}/>

      </Routes>
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
