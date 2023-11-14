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
    const [searchResults, setSearchResults] = useState([]);
    const [filterResults, setFilterResults] = useState([]);
    const [displayedMovies, setDisplayedMovies] = useState([]);
    const { userId } = useUserData();
    const [displayMode, setDisplayMode] = useState("all");

    const updateSearchResults = (results) => {
        setSearchResults(results);
        setDisplayMode("search");
    }

    const updateFilter = (resultsFilter) => {
        if (resultsFilter.length > 0) {
            const newDisplayedMovies = resultsFilter.filter(movie => !displayedMovies.includes(movie));
            setFilterResults(resultsFilter);
            setDisplayedMovies((prevMovies) => [...prevMovies, ...newDisplayedMovies]);
            setDisplayMode("filter");
        }
    }



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
        setMovies((prevMovies) => {
            return prevMovies.filter((movie) => movie.id !== movieId);
        });
    };

    let displayMovies = [];

    if (displayMode === "search" && searchResults.length > 0) {
        displayMovies = searchResults;
    } else if (displayMode === "filter" && filterResults.length > 0) {
        displayMovies = filterResults;
    } else {
        displayMovies = movies;
    }

    return (
        <>
            <SearchBar updateSearchResults={updateSearchResults} />
            <FilterBar updateFilter={updateFilter} displayedMovies={displayedMovies} />
            <div className="all_movie_container">
                {isLoading ? (
                    <Loader />
                ) : (
                    displayMovies.map((movie, index) => (
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
                    ))
                )}
            </div>
        </>
    );
};

export default Home;
