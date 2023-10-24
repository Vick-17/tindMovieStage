import React, { useState } from "react";
import CheckIcon from "@mui/icons-material/Check";
import IconButton from "@mui/material/IconButton";
import { setWatchedMovie } from "../../service/apiService";
import toast from "react-hot-toast";
import Modal from "./Modal";
import ThumbsUpDownIcon from '@mui/icons-material/ThumbsUpDown';

const Playlist = ({ id, titre, userId, filmId, showNotes, showLike }) => {
  const [isWatched, setIsWatched] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleCheckClick = async () => {
    try {
      const watchedData = {
        userId: userId,
        filmId: filmId,
      };
      await setWatchedMovie(watchedData);
      setIsWatched(true);
    } catch (error) {
      toast.error("Erreur lors de la mise a jours des film");
    }
  };

  const openModalRating = () => {
    setIsModalOpen(true)
  }

  const closeModal = () => {
    setIsModalOpen(false);
  };

  if (isWatched) {
    return null;
  }

  return (
    <div className="table_container">
      <div className="table-scroll">
        <table className="rwd-table">
          <thead>
            <tr>
              <th>Title</th>
              <th>{showNotes ? "Note" : "Regardé?"}</th>
            </tr>
          </thead>
          <tbody>
            <tr key={id}>
              <td data-th="Movie Title">{titre}</td>
              <td data-th="Wathed">
                {showNotes ? (
                  <>
                    <IconButton onClick={openModalRating} style={{ padding: "0", color: "black" }}>
                      <ThumbsUpDownIcon  />
                    </IconButton>
                  </>
                ) : (
                  <>
                    <IconButton onClick={handleCheckClick} style={{ padding: "0", color: "black" }}>
                      <CheckIcon />
                    </IconButton>
                  </>
                )}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <Modal 
      userId={userId}
      filmId={filmId}
      show={isModalOpen} onClose={closeModal} />
    </div>
  );
};

export default Playlist;
