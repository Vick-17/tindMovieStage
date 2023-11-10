import React, { useEffect, useState } from 'react';
import Rating from '@mui/material/Rating';
import Stack from '@mui/material/Stack';
import { getAllActor, getAllReal, getAllGenre, getGenreFilter, getRealisatorFilter, getActorFilter } from '../../service/apiService';

const FilterBar = ({ updateFilter }) => {
    const [actors, setActors] = useState([]);
    const [realisators, setRealisators] = useState([]);
    const [genres, setGenres] = useState([]);
    const [selectedGenre, setSelectedGenre] = useState("all");
    const [selectedActor, setSelectedActor] = useState("all");
    const [selectedReal, setSelectedReal] = useState("all");



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

    const getMoviesByGenre = async (genreId) => {
        try {
            const genreFilterResponse = await getGenreFilter(genreId);
            updateFilter(genreFilterResponse);
        } catch (e) {
            console.error("Une erreur s'est produite : ", e);
        }
    }

    const getMoviesByActor = async (actorId) => {
        try {
            const actorFilterResponse = await getActorFilter(actorId);
            updateFilter(actorFilterResponse);
        } catch (e) {
            console.error("Une erreur s'est produite : ", e);
        }
    }

    const getMoviesByReal = async (realId) => {
        try {
            const realFilterResponse = await getRealisatorFilter(realId);
            updateFilter(realFilterResponse);
        } catch (e) {
            console.error("Une erreur s'est produite : ", e);
        }
    }


    // Gestionnaire d'événements pour le changement de sélection de genre
    const handleGenreChange = (event) => {
        const selectedGenreId = event.target.value;
        setSelectedGenre(selectedGenreId);
        if (selectedGenreId !== "all") {
            getMoviesByGenre(selectedGenreId);
        }
    }

    const handleActorChange = (event) => {
        const selectedActorId = event.target.value;
        setSelectedActor(selectedActorId);
        if (selectedActorId !== "all") {
            getMoviesByActor(selectedActorId);
        }
    }

    const handleRealChange = (event) => {
        const selectedRealId = event.target.value;
        setSelectedActor(selectedRealId);
        if (setSelectedReal !== "all") {
            getMoviesByReal(selectedRealId);
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
                <select className='filter_select' value={selectedActor} onChange={handleActorChange}>
                    <option value="all">Toutes les acteurs</option>
                    {actors.map((actor, index) => (
                        <option key={index} value={actor.id}> {actor.actorName} </option>
                    ))}
                </select>
            </label>

            <label>
                Realisateur:
                <select className='filter_select' value={selectedReal} onChange={handleRealChange} >
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

