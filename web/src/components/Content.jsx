import React from 'react';
import './Content.css';



export default function Content({ banner }) {
    const { poster, title } = banner;
    return (
    <div className = "contentContainer">
          <div className = "p-3 contenido">
            <img className = 'poster' src = {poster} alt={title}/>
            <h5 className = 'text-light text-capitalize title'>{title} </h5>
        </div>
    </div>    
    );
  }


