import React, { useState } from "react";
import AddCircleIcon from '@mui/icons-material/AddCircle';

const Movie = ({ title, date, image, synopsis, director, actors }) => {
  const [isDescriptionVisible, setDescriptionVisible] = useState(false);

  const toggleDescription = () => {
    setDescriptionVisible(!isDescriptionVisible);
  };

  return (
    <div className="movie_card">
      <div className="card_content">
        <img
          src={image}
          alt={`affiche ${title}`}
        />
        <div className="card_titre_movie">
          <h3>
            {title}, {date}
          </h3>
          <div className="icon_info">
            <AddCircleIcon
              style={{ fontSize: 30 }}
              onClick={toggleDescription}
            />
          </div>
        </div>
        {isDescriptionVisible && (
          <div className="card_descr_movie">
            <div className="movie_title">
              <h3>{title}</h3>
            </div>
            <div className="movie_synopsis">
              <h5>Synopsis :</h5>
              <p>{synopsis}</p>
              <div className="movie_detail">
                <h5>Réalisateur :</h5>
                <p>{director}</p>
              </div>
              <div className="movie_detail">
                <h5>Acteurs :</h5>
                <p>{actors}</p>
              </div>
              <div className="svg_return"></div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Movie;
