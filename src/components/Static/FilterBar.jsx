import React, { useEffect, useState } from 'react';
import Rating from '@mui/material/Rating';
import Stack from '@mui/material/Stack';
import { getAllActor, getAllReal, getAllGenre } from '../../service/apiService';


const FilterBar = () => {
    const [ actors, setActors ] = useState([]);
    const [realisators, setRealisators] = useState([]);
    const [ genres , setGenres ] = useState([]);

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
    }, [])
    
    return (
        <div className='filter_container'>
            <label>
                Genre:
                <select className='filter_select'>
                    <option value="all">Tous les genres</option>
                    {genres.map((genre, index) => (
                        <option key={index} value={genre.id}> {genre.nomGenre} </option>
                    ))}
                    {/* Ajoutez d'autres genres au besoin */}
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