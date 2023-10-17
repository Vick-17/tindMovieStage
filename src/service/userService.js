import { useState, useEffect, useMemo, useRef } from "react";
import jwtDecode from "jwt-decode";
import { getUserIdByEmail } from "./apiService";

export const useUserData = () => {
  const [userEmail, setUserEmail] = useState("");
  const [userId, setUserId] = useState();
  const [userRole, setUserRole] = useState([]);

  const token = useRef("");

  useEffect(() => {
    token.current = localStorage.getItem("userToken");
    if (token.current !== null) {
      const decodedToken = jwtDecode(token.current);
      setUserEmail(decodedToken.sub);
      setUserRole(decodedToken.roles);
      getUserIdByEmail(decodedToken.sub).then((id) => {
        setUserId(id);
      });
    }
  }, []);

  const memoUser = useMemo(() => {
    return {
      userId,
      userEmail,
      userRole
    };
  }, [userId, userEmail, userRole]);

  return memoUser;
};
