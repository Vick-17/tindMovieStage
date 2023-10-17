import React, { useState } from "react";
import HeaderMessage from "../components/Static/HeaderMessage";
import MovieRate from "../components/Movie/MovieRate";
import { useParams } from "react-router-dom";
import { useEffect } from "react";
import { getMovieById } from "../service/apiService";

const Rating_Movie = () => {
  const [movieInfo, setMovieInfo] = useState(null);
  const { movieId } = useParams();
  const movieAffiche = movieInfo ? movieInfo.image : "URL_DE_REMPLISSAGE";


  useEffect(() => {
    getMovieById(movieId)
      .then((response) => {
        if (response.error) {
          console.log(
            "Erreur lors de la récupération des informations du film"
          );
        } else {
          setMovieInfo(response);
        }
      })
      .catch((error) => {
        console.error(error);
      });
  }, [movieId]);

  return (
    <div>
      <HeaderMessage title={movieInfo ? movieInfo.titre : "Titre inconnu"} />
      <MovieRate
        movieAffiche={movieAffiche}
      />
    </div>
  );
};

export default Rating_Movie;
