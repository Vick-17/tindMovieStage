import React from 'react';
import SearchBar from '../components/Static/SearchBar';

const NotFound = () => {
    return (
        <div>
            <SearchBar />
            <p style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}> Page non trouver</p>
        </div>
    );
};

export default NotFound;