import React, { useEffect, useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import MovieRate from "./page/Rating_Movie";
import LoginPage from "./page/LoginPage";
import Profil from "./page/Profil";
import ChatRoom from "./components/Users/ChatRoom";
import Home from "./page/Home";
import NotFound from "./page/NotFound";
import jwtDecode from "jwt-decode";

const App = () => {
  const [roles, setRoles] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("userToken");
    if (token !== null) {
      const decodedToken = jwtDecode(token);
      setRoles(decodedToken.roles);
    }
    setLoading(false);
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/commentaire/:movieId" element={<MovieRate />} />
      <Route path="/404" element={<NotFound />} />
      <Route path="/login" element={<LoginPage />} />
      
      {roles.length > 0 && roles[0] === "ROLE_MODO" ? (
        <Route path="/message" element={<ChatRoom />} />
      ) : (
        <Route path="/404" element={<NotFound />} />
      )}

      {roles.length > 0 && roles[0] === "ROLE_USER" ? (
        <Route path="/profil" element={<Profil />} />
      ) : (
        <Route path="/profil" element={<Navigate to="/login" />} />
      )}
    </Routes>
  );
}

export default App;
