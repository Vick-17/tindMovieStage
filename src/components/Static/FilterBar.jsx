import React, { useEffect, useState } from 'react';
import Rating from '@mui/material/Rating';
import Stack from '@mui/material/Stack';
import { getAllActor, getAllReal, getAllGenre, genreFilter } from '../../service/apiService';

const FilterBar = ({ updateFilter }) => {
    const [actors, setActors] = useState([]);
    const [realisators, setRealisators] = useState([]);
    const [genres, setGenres] = useState([]);
    const [selectedGenre, setSelectedGenre] = useState("all"); // State pour stocker le genre sélectionné

    useEffect(() => {
        const fetchData = async () => {
            try {
                const actorResponse = await getAllActor();
                const realResponse = await getAllReal();
                const genreResponse = await getAllGenre();
                setActors(actorResponse);
                setRealisators(realResponse);
                setGenres(genreResponse)
            } catch (e) {
                console.error("Une erreur s'est produite : ", e);
            }
        };
        fetchData();
    }, []);

    const getMovieByFilter = async (genreId) => {
        try {
            const genreFilterResponse = await genreFilter(genreId);
            updateFilter(genreFilterResponse);
        } catch (e) {
            console.error("Une erreur s'est produite : ", e);
        }
    }

    // Gestionnaire d'événements pour le changement de sélection de genre
    const handleGenreChange = (event) => {
        const selectedGenreId = event.target.value;
        setSelectedGenre(selectedGenreId);
        if (selectedGenreId !== "all") {
            getMovieByFilter(selectedGenreId);
        }
    }

    return (
        <div className='filter_container'>
            <label>
                Genre:
                <select className='filter_select' value={selectedGenre} onChange={handleGenreChange}>
                    <option value="all">Tous les genres</option>
                    {genres.map((genre, index) => (
                        <option key={index} value={genre.id}> {genre.nomGenre} </option>
                    ))}
                </select>
            </label>

            <label>
                Acteur:
                <select className='filter_select'>
                    <option value="all">Toutes les acteurs</option>
                    {actors.map((actor, index) => (
                        <option key={index} value={actor.id}> {actor.actorName} </option>
                    ))}
                </select>
            </label>
            <label>
                Realisateur:
                <select className='filter_select'>
                    <option value="all">Toutes les reals</option>
                    {realisators.map((realisator, index) => (
                        <option key={index} value={realisator.id}> {realisator.realisatorName} </option>
                    ))}
                </select>
            </label>
            <Stack spacing={1}>
                <Rating name="half-rating" defaultValue={2.5} precision={0.5} />
            </Stack>
        </div>
    );
};

export default FilterBar;

