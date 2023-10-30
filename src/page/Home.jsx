import React, { useEffect, useState } from 'react';
import CardList from '../components/Movie/CardList';
import SearchBar from '../components/Static/SearchBar';
import { getAllMovie } from '../service/apiService';
import Loader from "../components/Static/Loader";
import { useUserData } from '../service/userService';

const Home = () => {
    const [movies, setMovies] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const { userId } = useUserData();
    

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await getAllMovie();
                setMovies(response);
                setIsLoading(false);
            } catch (e) {
                console.error("Une erreur s'est produite : ", e);
                setIsLoading(false);
            }
        };

        fetchData();
    }, []);

    return (
        <>
            <SearchBar />
            <div className="all_movie_container">
                {isLoading ? (
                    <Loader />
                ): (
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
                    />
                   ))}
                   </>
                )}
            </div>
        </>
    );
};

export default Home;