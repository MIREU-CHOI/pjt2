import React, { useCallback, useEffect, useState } from 'react';

const SidebarMR2 = () => {
    return (
        <div className="sidebar sidebar-hide-to-small sidebar-shrink sidebar-gestures">
            <div className="nano">
                <div className="nano-content">
                    <div className="logo"><a href="index.html">
                        <img src={'https://www.e4net.net/img/logo.png'} alt="" />
                        <span>Focus</span></a></div>
                    <ul>
                        <li className="label">Main</li>
                        <li><a className="sidebar-sub-toggle"><i className="ti-home"></i> Dashboard <span className="badge badge-primary">2</span> <span className="sidebar-collapse-icon ti-angle-down"></span></a>
                            <ul>
                                <li><a href="index.html">Dashboard 1</a></li>
                                <li><a href="index.html">Dashboard 2</a></li>
                                
                                
                                
                            </ul>
                        </li>

                        <li className="label">Apps</li>
                        <li><a className="sidebar-sub-toggle"><i className="ti-bar-chart-alt"></i>  Charts  <span className="sidebar-collapse-icon ti-angle-down"></span></a>
                            <ul>
                                <li><a href="chart-flot.html">Flot</a></li>
                                <li><a href="chart-morris.html">Morris</a></li>
                                <li><a href="chartjs.html">Chartjs</a></li>
                                <li><a href="chartist.html">Chartist</a></li>
                                <li><a href="chart-peity.html">Peity</a></li>
                                <li><a href="chart-sparkline.html">Sparkle</a></li>
                                <li><a href="chart-knob.html">Knob</a></li>
                            </ul>
                        </li>
                        <li><a href="app-event-calender.html"><i className="ti-calendar"></i> Calender </a></li>
                        <li><a href="app-email.html"><i className="ti-email"></i> Email</a></li>
                        <li><a href="app-profile.html"><i className="ti-user"></i> Profile</a></li>
                        <li><a href="app-widget-card.html"><i className="ti-layout-grid2-alt"></i> Widget</a></li>

                        </ul>
                </div>
            </div>
        </div>
        // <!-- /# sidebar -->
        
    );
};

export default SidebarMR2;