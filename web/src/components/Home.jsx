import React, { useState, useEffect } from 'react';
import Navigation from './Navigation';
import { useLocation } from "react-router-dom";
import PostersView from './PostersView'
import './Home.css'
import Api from './Api'


export default function Home() {
    const location = useLocation();
    const { authorization } = location.state;
    const [favorites, setFavorites] = useState([]);
    const [lastSeen, setLastSeen] = useState([]);
    const [name, setName] = useState('')

    useEffect(() => {
        Api.getUser(authorization)
          .then(response => {
              console.log(response.data);
              setFavorites(response.data.favorites);
              setLastSeen(response.data.lastSeen);
              setName(response.data.name)      
            })
          .catch(() => console.log('Boom'));
      }, []);

    return (
        
        <div className = "container">
            <Navigation isLogged={true} />
            <h1 className = "saludo">
                HI {name}!
            </h1>
            <PostersView content = {favorites} text = 'Favoritos'/>
            <PostersView content = {lastSeen} text = 'Ultimos vistos'/>
        </div>
    )
}

