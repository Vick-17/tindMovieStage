import React, { useState } from "react";
import MessageIcon from "@mui/icons-material/Message";
import IconButton from "@mui/material/IconButton";
import SettingsIcon from "@mui/icons-material/Settings";
import Sidebar from "./Sidebar";

const Header = () => {
  const [showSidebar, setShowSidebar] = useState(false);

  const handleSidebarToggle = () => {
    setShowSidebar(!showSidebar);
  };

  const handleCloseSidebar = () => {
    setShowSidebar(false);
  };

  return (
    <header>
    {showSidebar && <Sidebar onClose={handleCloseSidebar} />}
    <div className="message_icon">
      <a href="/dev">
        <IconButton>
          <MessageIcon style={{ fontSize: 40, color: "#1C9CEA" }} />
        </IconButton>
      </a>
    </div>
    <div className="burger_icon">
      <IconButton onClick={handleSidebarToggle}>
        <SettingsIcon style={{ fontSize: 40, color: "#1C9CEA" }} />
      </IconButton>
    </div>
  </header>
  );
};

export default Header;
