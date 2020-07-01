import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import Navigation from './Navigation';
import Content from './Content';
import './ContentView.css'



export default function ContentView(){
    const location = useLocation();
    const content = location.state



    return(
        <div className="container">
        <Navigation isLogged = {true} searchBarMode = {true}/>    
        <div className = 'modalContainer'>
            <h2 className = "text">Contenidos</h2>
            <div className = "banners">
                {content.map( banner => (<Content key={banner.id} banner = {banner}/>))}
            </div>
        </div>
        </div>

    )

}