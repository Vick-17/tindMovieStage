import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { useUserData } from "../../service/userService";
import { getPartenaire } from "../../service/apiService"

const HeaderMessage = ({ title }) => {
  const location = useLocation();
  const isMoviePage = location.pathname.includes("/commentaire/");
  const { userId } = useUserData();
  const [partenaireUsername, setPartenaireUsername] = useState("");
  useEffect(() => {
    const fetchData = async () => {
      if (userId) {
        try {
          setPartenaireUsername(await getPartenaire(userId));
        } catch (error) {
          console.error(error);
        }
      }
    };

    fetchData();
  }, [userId]);

  return (
    <div className="header_message">
      <div className="svg_return">
        <a href="/">
          <svg
            width="24px"
            height="24px"
            viewBox="0 0 24 24"
            strokeWidth="1.5"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            color="#000000"
          >
            <path
              d="M21 12H3m0 0l8.5-8.5M3 12l8.5 8.5"
              stroke="#000000"
              strokeWidth="1.5"
              strokeLinecap="round"
              strokeLinejoin="round"
            ></path>
          </svg>
        </a>
      </div>
      <div className="user_message_name">
        <h5>{isMoviePage ? title : partenaireUsername}</h5>
      </div>
    </div>
  );
};

export default HeaderMessage;
