import React from "react";
import ReplayIcon from "@mui/icons-material/Replay";
import IconButton from "@mui/material/IconButton";
import CancelOutlinedIcon from "@mui/icons-material/CancelOutlined";
import StarIcon from "@mui/icons-material/Star";
import FavoriteIcon from "@mui/icons-material/Favorite";

const iconSwipe = ({ onSwipeLeft, onSwipeRight, onReturn, movieId }) => {
  const handleStarIconClick = () => {
    if (movieId) {
      window.location.href = `/commentaire/${movieId}`;
    }
  };
  return (
    <div className="icon_swipe_container">
      <IconButton onClick={onReturn}>
        <ReplayIcon style={{ fontSize: 40, color: "#1C9CEA" }} />
      </IconButton>

      <IconButton onClick={onSwipeLeft}>
        <CancelOutlinedIcon style={{ fontSize: 40 }} />
      </IconButton>

      <IconButton onClick={handleStarIconClick}>
        <StarIcon
          style={{ fontSize: 40, color: "#FFAC33" }}
        />
      </IconButton>

      <IconButton onClick={onSwipeRight}>
        <FavoriteIcon style={{ fontSize: 40, color: "#E74C3C" }} />
      </IconButton>
    </div>
  );
};

export default iconSwipe;
