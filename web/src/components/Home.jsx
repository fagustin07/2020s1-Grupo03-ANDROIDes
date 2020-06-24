import React from 'react';
import Navigation from './Navigation';
import { useLocation } from "react-router-dom";
import BannersPreview from './BannersPreview'


export default function Home() {
    const location = useLocation();
    const user = location.state

    return (
        <div className = "container">
            <Navigation isLogged={true} />
            <h1>
                HI {user.user}!
            </h1>
            <BannersPreview/>
        </div>
    )
}

