import React from "react";
import HeaderProfil from "../components/Users/HeaderProfil";
import SearchBar from "../components/Static/SearchBar";
import UserPlaylist from "../components/Users/userPlaylist";

const Profil = () => {
  return (
    <>
      <SearchBar />
      <HeaderProfil />
      <UserPlaylist />
    </>
  );
};

export default Profil;
