/*!

=========================================================
* Paper Dashboard React - v1.3.2
=========================================================

* Product Page: https://www.creative-tim.com/product/paper-dashboard-react
* Copyright 2023 Creative Tim (https://www.creative-tim.com)

* Licensed under MIT (https://github.com/creativetimofficial/paper-dashboard-react/blob/main/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import React from "react";
import { NavLink, useLocation } from "react-router-dom";
import { Nav } from "reactstrap";
import PerfectScrollbar from "perfect-scrollbar";

import logo from "logo.svg";

var ps;

function Sidebar(props) {
  const location = useLocation();
  const sidebar = React.useRef();
  const activeRoute = (routeName) => {
    return location.pathname === routeName ? "active" : "";
  };
  React.useEffect(() => {
    if (navigator.platform.indexOf("Win") > -1) {
      ps = new PerfectScrollbar(sidebar.current, {
        suppressScrollX: true,
        suppressScrollY: false,
      });
    }
    return function cleanup() {
      if (navigator.platform.indexOf("Win") > -1) {
        ps.destroy();
      }
    };
  });
  return (
      <div
          className="sidebar"
          data-color={props.bgColor}
          data-active-color={props.activeColor}
      >
        <div className="logo">
          <a
              href=""
              className="simple-text logo-mini"
          >
            <div className="logo-img">
              <img src={logo} alt="school-logo" />
            </div>
          </a>
          <a
              href=""
              className="simple-text logo-normal"
          >
            Classwise
          </a>
        </div>
        <div className="sidebar-wrapper" ref={sidebar}>
          <Nav>
            {props.routes.map((prop, key) => {
              return (
                  <li
                      className={
                          activeRoute(prop.path) + (prop.pro ? " active-pro" : "")
                      }
                      key={key}
                  >
                    <NavLink to={prop.layout + prop.path} className="nav-NavLink d-flex align-items-center">
                      <i>{prop.icon}</i>
                      <p>{prop.name}</p>
                    </NavLink>
                  </li>
              );
            })}
          </Nav>
        </div>
      </div>
  );
}

export default Sidebar;
