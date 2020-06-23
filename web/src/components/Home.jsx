import React from 'react';
import Navigation from './Navigation';
import { useLocation } from "react-router-dom";

export default function Home() {
    const location = useLocation();
    const user = location.state

    return (
        <div>
            <Navigation isLogged={true} />
        <h1>
            HI {user.user}!
        </h1>
        </div>
    )
}