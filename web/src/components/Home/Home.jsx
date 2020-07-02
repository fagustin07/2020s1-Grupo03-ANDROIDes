import React, { useState, useEffect } from 'react';
import BannersCarrusel from './BannersCarrusel';
import Navigation from '../Navigation';
import API from '../Api'
import MainCarrusel from './MainCarrusel';


export default function Home() {
    const [favorites, setFavorites] = useState([]);
    const [lastSeen, setLastSeen] = useState([]);
    const [name, setName] = useState('')

    useEffect(() => {
        API.getUser()
          .then(response => {
              setFavorites(response.data.favorites);
              setLastSeen(response.data.lastSeen);
              setName(response.data.name)
            })
          .catch(() => console.log('Boom'));
      }, []);

    return (
        <div className = "container">
            <Navigation isLogged={true}/>
            <h1 className = "saludo">
                Hi {name.split(" ",1)}!
            </h1>
            <MainCarrusel/>
            <BannersCarrusel content = {favorites} text = "Favoritos" />
            <BannersCarrusel content = {lastSeen} text = "Last Seen"/>
        </div>
    )
}

