import React from "react";
import HeaderProfil from "../components/Users/HeaderProfil";
import Header from "../components/Static/header";
import UserPlaylist from "../components/Users/userPlaylist";

const Profil = () => {
  return (
    <>
      <Header />
      <HeaderProfil />
      <UserPlaylist />
    </>
  );
};

export default Profil;
