import React, { useRef, useState } from "react";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import GroupIcon from "@mui/icons-material/Group";
import LocalMoviesIcon from "@mui/icons-material/LocalMovies";
import IconButton from "@mui/material/IconButton";
import AssignmentIcon from "@mui/icons-material/Assignment";
import { Link } from "react-router-dom";
import HomeIcon from "@mui/icons-material/Home";
import { partenaireLink, getLinkedUsername } from "../../service/apiService";
import { useUserData } from "../../service/userService";
import { toast, Toaster } from "react-hot-toast";
import CloseIcon from "@mui/icons-material/Close";

const Sidebar = ({ onClose }) => {
  const { userId, userRole } = useUserData();
  const [showSidebar] = useState(true);
  const inputRef = useRef();

  const handleCloseSidebar = () => {
    if (onClose) {
      onClose();
    }
  };
  const handleLogout = () => {
    // Supprimer le token du local storage
    localStorage.removeItem("userToken");

    // Rediriger l'utilisateur vers la page de connexion
    window.location.href = "/";
  };

  const handlSubmit = async () => {
    const shareCode = inputRef.current.value;
    try {
      const response = await partenaireLink(shareCode, userId);

      if (response.ok) {
        toast.success("Liaison avec le partenaire réussie.");
      } else {
        const errorMessage = await response.text();
        toast.error(errorMessage);
      }

      const linkedUserResponse = await getLinkedUsername(userId);
      if (linkedUserResponse.ok) {
        const linkedUsername = await linkedUserResponse.text();
        toast.success(`Vous êtes maintenant lié avec : ${linkedUsername}`);
      } else {
        toast.error("Erreur lors de la récupération du partenaire");
      }
    } catch (error) {
      console.error(error);
      toast.error(
        "Une erreur s'est produite lors de la liaison avec le partenaire."
      );
    }
  };



  if (!userRole) {
    return <div>Chargement en cours...</div>;
  }

  return (
    <div className={`sidebar ${showSidebar ? "show" : ""}`}>
      <Toaster position="top-center" reverseOrder={false} />
      <div className="sidebar_content">
        <ul>
          <IconButton
            style={{ fontSize: "17px", color: "black" }}
            onClick={handleCloseSidebar}
          >
            <CloseIcon />
          </IconButton>
          <li>
            <Link to="/">
              <IconButton style={{ fontSize: "17px", color: "black" }}>
                <HomeIcon style={{ color: "#1C9CEA", margin: "5px" }} /> 
                Accueil
              </IconButton>
            </Link>
          </li>
          <li>
            <Link to="/profil">
              <IconButton style={{ fontSize: "17px", color: "black" }}>
                <AccountBoxIcon style={{ color: "#1C9CEA", margin: "5px" }} />
                Mon compte
              </IconButton>
            </Link>
          </li>
          <li>
            <Link to="/dev">
              <IconButton style={{ fontSize: "17px", color: "black" }}>
                <LocalMoviesIcon style={{ color: "#1C9CEA", margin: "5px" }} />
                Tous les films
              </IconButton>
            </Link>
          </li>
          <li>
            <Link to="/dev">
              <IconButton style={{ fontSize: "17px", color: "black" }}>
                <AssignmentIcon style={{ color: "#1C9CEA", margin: "5px" }} /> A
                propos
              </IconButton>
            </Link>
          </li>
          <li style={{ display: "flex", flexDirection: "column" }}>
            <GroupIcon style={{ color: "#1C9CEA", margin: "5px" }} />
            <input
              type="text"
              ref={inputRef}
              placeholder="Ajouter un code partenaire"
            />
            <IconButton
              onClick={handlSubmit}
              style={{ fontSize: "17px", color: "black" }}
            >
              Ajouter
            </IconButton>
          </li>
        </ul>
        {userRole.includes("ROLE_USER") || userRole.includes("ROLE_ADMIN") ? (
          <button className="btn_go_login" onClick={handleLogout}>
            <Link to="/">Déconnexion</Link>
          </button>
        ) : (
          <button className="btn_go_login">
            <Link to="/login">Connexion</Link>
          </button>
        )}
      </div>
    </div>
  );
};

export default Sidebar;
