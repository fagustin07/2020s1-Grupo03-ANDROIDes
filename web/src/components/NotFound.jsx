import React from 'react';
import Navigation from './Navigation';

export default function NotFound(){

    return(
        <div className="bg-light">
            <Navigation isLogged={false} />
            <h1 className="font-bolder">404 Not Found.</h1>
        </div>
    )
}