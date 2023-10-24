import React, { useEffect, useState } from "react";
import Playlist from "../Movie/Playlist";
import IconButton from "@mui/material/IconButton";
import {
  getSwipeByUserId,
  getWatchedMovieByUserId,
  getRecommendationByuser
} from "../../service/apiService";
import { useUserData } from "../../service/userService";
import FullscreenIcon from "@mui/icons-material/Fullscreen";
import Loader from "../Static/Loader";

const UserPlaylist = () => {
  const { userId } = useUserData();
  const [swipe, setSwipe] = useState([]);
  const [recommendMovies, setRecommendMovies] = useState([])
  const [watchedMovies, setWatchedMovies] = useState([]);
  const [fullScreen, setFullScreen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (!userId) {
      // userId n'est pas encore défini, ne rien faire
      return;
    }

    getSwipeByUserId(userId)
      .then((response) => {
        setSwipe(response);
      })
      .catch((error) => {
        console.error("Erreur c'est produite :", error);
      });

    getRecommendationByuser(userId)
      .then((response) => {
        setRecommendMovies(response);
      })
      .catch((error) => {
        console.error("Erreur c'est produite :", error);
      })
    getWatchedMovieByUserId(userId)
      .then((response) => {
        setWatchedMovies(response);
      })
      .catch((error) => {
        console.error("Erreur c'est produite :", error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [userId]);

  const toggleFullScreen = () => {
    setFullScreen(!fullScreen);
  };

  if (isLoading) {
    return <Loader />;
  }

  return (
    <>
      <div className="user_block_container">
        <div className="header_playlist">
          <h5>Notre playlist</h5>
          <IconButton onClick={toggleFullScreen} style={{ padding: "0" }}>
            <FullscreenIcon />
          </IconButton>
        </div>
        <div className={`playlist ${fullScreen ? "fullScreen" : ""}`}>
          {swipe.map((movie, index) => (
            <Playlist
              key={movie.id}
              titre={movie.titre}
              userId={userId}
              filmId={movie.id}
            />
          ))}
        </div>
      </div>
      <div className="user_block_container">
        <h5>Suggestion</h5>
        <div className={`playlist ${fullScreen ? "fullScreen" : ""}`}>
          {recommendMovies.map((movie, index) => (
            <Playlist
              key={movie.id}
              titre={movie.titre}
              userId={userId}
              filmId={movie.id}
              showLike={true}
            />
          ))}
        </div>
      </div>
      <div className="user_block_container">
        <h5>Films vus</h5>
        <div className={`playlist ${fullScreen ? "fullScreen" : ""}`}>
          {watchedMovies.map((watchedMovie, index) => (
            <Playlist
              key={watchedMovie.id}
              titre={watchedMovie.titre}
              userId={userId}
              filmId={watchedMovie.id}
              showNotes={true}
            />
          ))}
        </div>
      </div>
    </>
  );
};

export default UserPlaylist;
