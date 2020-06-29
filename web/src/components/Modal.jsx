import React, { useState } from 'react';
import Content from './Content';
import './Modal.css'
import { useLocation } from 'react-router-dom';
import PostersView from './PostersView';
import Navigation from './Navigation';

export default function ModalSearch(){
    const location = useLocation();
    const content = location.state

    return(
        <div>
        <Navigation isLogged = {true}/>    
        <div className = 'modalContainer'>
            <div className = "banners">
                {content.map( banner => (<Content banner = {banner}/>))} 
                {console.log(content)}
            </div>
        </div>
        </div>

    )

}