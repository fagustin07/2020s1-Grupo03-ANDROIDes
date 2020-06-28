import React from 'react';
import './Content.css';



export default function Content({ banner }) {
    const { poster, title } = banner;
    return (
    <div className = "contentContainer">
          <div className = "contenido">
            <img className = 'poster 'src = {poster}/>
            <h5 className = 'title'>{title} </h5>
        </div>
    </div>    
    );
  }


