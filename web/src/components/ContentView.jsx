import React, { useState } from 'react';
import Navigation from './Navigation';
import Content from './Content';
import './ContentView.css'
import { useEffect } from 'react';
import API from './Api';


export default function ContentView(){
    const [content, setContent]= useState([]);

    useEffect(()=>{
        API.getContent()
        .then(response => setContent(response.data))
        .catch(error => console.log(error))
    },[]);


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