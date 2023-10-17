import React, { useEffect } from "react";
import Rating from "@mui/material/Rating";
import Comment from "./Comment";
import { useParams } from "react-router-dom";
import {
  getCommentForMovie,
  getNotMoyenne,
  getUserById,
} from "../../service/apiService";
import { useState } from "react";

const MovieRate = (movieAffiche) => {
  const { movieId } = useParams();
  const [comment, setComment] = useState([]);
  const [commentWithAuthors, setCommentWithAuthors] = useState([]);
  const [note, setNote] = useState();

  useEffect(() => {
    getNotMoyenne(movieId)
      .then((response) => {
        if (response.error) {
          console.log("marche pas");
        } else {
          setNote(response);
        }
      })
      .catch((error) => {
        console.error(error);
      });

    getCommentForMovie(movieId)
      .then((response) => {
        if (response.error) {
          console.log("marche pas non plus");
        } else {
          setComment(response);
        }
      })
      .catch((error) => {
        console.error(error);
      });
  }, [movieId]);

  useEffect(() => {
    const fetchUserNames = async () => {
      const commentsWithAuthors = await Promise.all(
        comment.map(async (commentaire) => {
          const userId = commentaire.usersId;
          const user = await getUserById(userId);
          return { ...commentaire, author: user.username };
        })
      );
      setCommentWithAuthors(commentsWithAuthors);
    };
    fetchUserNames();
  }, [comment]);
  return (
    <div className="movie_rate_container">
      <div className="rate_img_container">
      <img src={ movieAffiche.movieAffiche } alt="Affiche du film" />
      </div>
      <div className="rate_star_container">
        {note !== undefined ? (
          <Rating
            name="half-rating-read"
            value={note}
            precision={0.5}
            readOnly
          />
        ) : (
          <p>Chargement de la note...</p>
        )}
      </div>
      <div className="comment_container">
        {commentWithAuthors !== undefined ? (
          commentWithAuthors.map((commentaire, index) => (
            <Comment
              content={commentaire.content}
              author={commentaire.author || "Auteur inconnu"}
              key={index}
            />
          ))
        ) : (
          <p>Chargement des commentaire...</p>
        )}
      </div>
    </div>
  );
};

export default MovieRate;
