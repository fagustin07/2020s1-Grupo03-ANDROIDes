import React from 'react';
import './Content.css';

export default function Content({ banner }) {
    const { poster, title, description  } = banner;
    return (
    <div className = "contentContainer">
          <div className = "contenido">
            <img className = 'poster 'src = {poster}/>
            <h5 className = 'title'>{title} </h5>
        </div>
    </div>    
        // <div class="card">
        //     <img src={poster} className="card-img-top" alt="..."/>
        //     <div className="card-body">
        //         <p className="card-text">{title}</p>
        //     </div>
        // </div>
    
    );
  }


