import React, { useState } from "react";
import Header from "../components/Static/SearchBar";
import Login from "../components/Users/Login";
import SignIn from "../components/Users/SignIn";
import Link from "@mui/material/Link";

const LoginPage = () => {
  const [isLogin, setIslogin] = useState(true);

  const toggleComponent = () => {
    setIslogin(!isLogin);
  };

  return (
    <div className="login_container">
      <Header />
      {isLogin ? <Login /> : <SignIn />}
      <Link
        className="toggle_link"
        onClick={toggleComponent}
        href="#"
        variant="body2"
      >
        {isLogin
          ? "Pas de compte ? Inscriver-vous"
          : "Déja un compte ? Connecter-vous"}
      </Link>
    </div>
  );
};

export default LoginPage;
