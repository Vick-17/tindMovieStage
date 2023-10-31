import React, { useState, useEffect } from 'react';
import CardList from '../components/Movie/CardList';
import SearchBar from '../components/Static/SearchBar';
import { getAllMovie, getAllMovieByUser } from '../service/apiService';
import Loader from "../components/Static/Loader";
import { useUserData } from '../service/userService';
import FilterBar from '../components/Static/FilterBar';

const Home = () => {
    const [movies, setMovies] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const { userId } = useUserData();

    useEffect(() => {
        const fetchData = async () => {
            try {
                if (userId) {
                    const response = await getAllMovieByUser(userId);
                    setMovies(response);
                    setIsLoading(false);
                } else {
                    const response = await getAllMovie();
                    setMovies(response);
                    setIsLoading(false);
                }
            } catch (e) {
                console.error("Une erreur s'est produite : ", e);
                setIsLoading(false);
            }
        };

        fetchData();
    }, [userId]);

    const removeMovieFromList = (movieId) => {
        // Utilisez une fonction pour mettre à jour l'état
        setMovies((prevMovies) => {
            return prevMovies.filter((movie) => movie.id !== movieId);
        });
    };

    return (
        <>
            <SearchBar />
            <FilterBar />
            <div className="all_movie_container">
                {isLoading ? (
                    <Loader />
                ) : (
                    <>
                        {movies.map((movie, index) => (
                            <CardList
                                key={index}
                                title={movie.titre}
                                image={movie.image}
                                content={movie.synopsis}
                                subheader={movie.dates}
                                movieId={movie.id}
                                userId={userId}
                                onRemove={(movieId) => removeMovieFromList(movieId)}
                            />
                        ))}
                    </>
                )}
            </div>
        </>
    );
};

export default Home;
