import React, { useState } from "react";
import Slider from "@mui/material/Slider";
import TextField from "@mui/material/TextField";
import SendIcon from "@mui/icons-material/Send";
import Button from "@mui/material/Button";
import { addCommentAndNote } from "../../service/apiService";
import { useUserData } from "../../service/userService";

const Modal = ({ show, onClose, filmId }) => {
  const [noteValue, setNoteValue] = useState(0);
  const [comment, setComment] = useState("");
  const {userId} = useUserData();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const commentData = {
      content: comment,
      usersId: userId,
      filmId: filmId,
    };
  
    const noteData = {
      // Récupérer la valeur de la note depuis noteRef
      rating: noteValue,
      userId: userId,
      movieId: filmId,
    };
  
    // Appeler la fonction d'envoi de données à l'API
    try {
      await addCommentAndNote(commentData, noteData);
      onClose();  // Fermer la modal après l'envoi réussi
    } catch (error) {
      console.error("Erreur lors de l'envoi des données :", error);
      console.log("user : " + userId + " film : " + filmId + " comment : " + comment);
      // Gérer les erreurs ici si nécessaire
    }
  };

  if (!show) {
    return null;
  }

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>Noter le Film</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="noteSlider">{noteValue}/5</label>
            <Slider
              id="noteSlider"
              aria-label="Temperature"
              value={noteValue}
              onChange={(e, newValue) => setNoteValue(newValue)}
              valueLabelDisplay="auto"
              step={0.5}
              marks
              min={0}
              max={5}
            />
            <TextField
              id="outlined-textarea"
              label="Votre commentaire"
              placeholder="commentaire"
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              multiline
            />
          </div>
          <Button type="submit" variant="contained" endIcon={<SendIcon />}>
            Envoyer
          </Button>
        </form>
      </div>
    </div>
  );
};

export default Modal;
