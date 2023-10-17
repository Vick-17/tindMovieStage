import React from 'react';
import { Link } from 'react-router-dom';

const Test = () => {
    return (
        <div style={{ width:"100%", height: "100vh", display: "flex", flexDirection: "column", textAlign:"center", justifyContent: "center", alignItems: "center"}}>
            <h1>PAGE EN COUR DE DEVELOPPEMENT</h1>
            <Link to="/">
                Go back
            </Link>
        </div>
    );
};

export default Test;