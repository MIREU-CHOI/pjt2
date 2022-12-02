import React from 'react';

import SidebarItem from "./SidebarItem";
import {NavLink} from "react-router-dom";

function Sidebar() {

    const menus = [
        { name: "머니충전", path: "/charge" },
        { name: "머니 결제", path: "/pay" },
        { name: "거래 내역", path: "/history" },
    ];

    const activeStyle ={
        color: "red",
        opacity: 0.6,
        fontWeight: "bold",
        fontSize:"40px",
        textDecorationLine : "none"
    }

    const noneActiveStyle = {
        color : "black",
        textDecorationLine : "none"
    }

    return (
        <div className="sidebar_box">
        <div className="sidebar">
            {menus.map((menu, index) => {
                return (
                    <NavLink  to={menu.path} key={index}  style={({ isActive }) =>
                        isActive ? activeStyle : noneActiveStyle
                    }
                    >
                        <SidebarItem
                            menu={menu}
                        />
                    </NavLink>

                );
            })}

        </div>
        </div>
    );
}

export default Sidebar;