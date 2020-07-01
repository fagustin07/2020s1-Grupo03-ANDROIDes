import React, { useState, useEffect } from 'react';
import PostersView from './PostersView'
import Navigation from './Navigation';
import API from './Api'
import './Home.css'


export default function Home() {
    const [favorites, setFavorites] = useState([]);
    const [lastSeen, setLastSeen] = useState([]);
    const [banners, setBanners] = useState([]);
    const [name, setName] = useState('')

    useEffect(() => {
        API.getUser()
          .then(response => {
              setFavorites(response.data.favorites);
              setLastSeen(response.data.lastSeen);
              setName(response.data.name)
            })
          .catch(() => console.log('Boom'));

        API.getBanners()
            .then(response => setBanners(response.data))
            .catch(error => console.log(error));
      }, []);

    return (
        <div className = "container">
            <Navigation isLogged={true}/>
            <h1 className = "saludo">
                Hi {name.split(" ",1)}!
            </h1>
            <PostersView content = {banners} text='Banners' />
            <PostersView content = {favorites} text = 'Favoritos'/>
            <PostersView content = {lastSeen} text = 'Ultimos vistos'/>
        </div>
    )
}

