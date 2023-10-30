import React, { useEffect, useState } from 'react';
import CardList from '../components/Movie/CardList';
import Header from '../components/Static/header';
import SearchBar from '../components/Static/SearchBar';
import { getAllMovie } from '../service/apiService';

const Home = () => {
const [movie, setMovies] = useState([]);

useEffect(() => {
try {
    const response = await getAllMovie();

    if (response === 200) {
        setMovies(response)
    }
} catch (e) {
    
}
},[])

    return (
        <>
            <SearchBar />
            <div className="all_movie_container">

            </div>
        </>
    );
};

export default Home;