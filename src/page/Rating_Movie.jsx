import React, { useState } from "react";
import MovieRate from "../components/Movie/MovieRate";
import SearchBar from "../components/Static/SearchBar";
import { useParams } from "react-router-dom";
import { useEffect } from "react";
import { getMovieById } from "../service/apiService";
import Loader from "../components/Static/Loader";

const Rating_Movie = () => {
  const [affiche, setAffiche] = useState(null);
  const [title, setTitle] = useState(null);
  const { movieId } = useParams();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    getMovieById(movieId)
      .then((response) => {
        if (response.error) {
          console.log("Erreur lors de la récupération des informations du film");
        } else {
          setAffiche(response.image);
          setTitle(response.titre);
          setIsLoading(false);
        }
      })
      .catch((error) => {
        console.error(error);
        setIsLoading(false);
      });
  }, [movieId]);

  return (
    <div>
      <SearchBar />
      {isLoading ? (
        <Loader />
      ) : (
        (affiche && title) ? (
          <MovieRate movieAffiche={affiche} title={title} />
        ) : (
          <p>Les informations du film ne sont pas disponibles.</p>
        )
      )}
    </div>
  );
};

export default Rating_Movie;
