import React from 'react';
import Content from './Content';
import './Modal.css'
import { useLocation } from 'react-router-dom';
import Navigation from './Navigation';

export default function ModalSearch(){
    const location = useLocation();
    const content = location.state

    return(
        <div className="container">
        <Navigation isLogged = {true}/>    
        <div className = 'modalContainer'>
            <div className = "banners">
                {content.map( banner => (<Content key={banner.id} banner = {banner}/>))} 
                {console.log(content)}
            </div>
        </div>
        </div>

    )

}