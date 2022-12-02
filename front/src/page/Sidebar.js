import React from 'react';

import SidebarItem from "./SidebarItem";
import {NavLink} from "react-router-dom";
import {
    CDBSidebar,
    CDBSidebarContent,
    CDBSidebarFooter,
    CDBSidebarHeader,
    CDBSidebarMenu,
    CDBSidebarMenuItem,
  } from 'cdbreact';

  const Sidebar = () => {
    return (
      <div style={{ display: 'flex', height: '100vh', overflow: 'scroll initial' }}>
        <CDBSidebar textColor="#fff" backgroundColor="#333">
          <CDBSidebarHeader prefix={<i className="fa fa-bars fa-large"></i>}>
            <img
              src={'https://www.e4net.net/img/logo.png'}
              alt=""
              style={{ width: '40px' }}
            />
            <a href="/" className="text-decoration-none" style={{ fontSize:'20px', color: 'inherit' }}>
              &nbsp; Pay Service &nbsp;
            </a>
          </CDBSidebarHeader>
  
          <CDBSidebarContent className="sidebar-content">
            <CDBSidebarMenu>
              <NavLink exact="true" to="/charge" activeclassname="activeClicked">
                <CDBSidebarMenuItem icon="columns">머니 충전</CDBSidebarMenuItem>
              </NavLink>
              {/* <NavLink exact="true" to="/pay" activeclassname="activeClicked">
                <CDBSidebarMenuItem icon="table">머니 결제</CDBSidebarMenuItem>
              </NavLink> */}
              {/* 미르 테스트  */}
              <NavLink exact="true" to="/payment" activeclassname="activeClicked">
                <CDBSidebarMenuItem icon="table">머니 결제</CDBSidebarMenuItem>
              </NavLink>
              <NavLink exact="true" to="/history" activeclassname="activeClicked">
                <CDBSidebarMenuItem icon="user">거래 내역</CDBSidebarMenuItem>
              </NavLink>
              {/* <NavLink exact to="/analytics" activeClassName="activeClicked">
                <CDBSidebarMenuItem icon="chart-line">Analytics</CDBSidebarMenuItem>
              </NavLink>
  
              <NavLink exact to="/hero404" target="_blank" activeClassName="activeClicked">
                <CDBSidebarMenuItem icon="exclamation-circle">404 page</CDBSidebarMenuItem>
              </NavLink> */}
            </CDBSidebarMenu>
          </CDBSidebarContent>
  
          <CDBSidebarFooter style={{ textAlign: 'center' }}>
            <div
              style={{
                padding: '20px 5px',
              }}
            >
              E4NET
              {/* Sidebar Footer */}
            </div>
          </CDBSidebarFooter>
        </CDBSidebar>
      </div>
    );
  };
  
  export default Sidebar;