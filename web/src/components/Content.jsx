import React from 'react';
import { useHistory } from 'react-router';
import './Content.css';

export default function Content({ banner }) {

    const { poster, title, id } = banner;
    const history = useHistory();

    const goToDetails = () => history.push(`/content/${id}`)

    return (
    <div className = "contentContainer">
          <div className = "p-4 contenido">
            <img className = 'poster' src = {poster} alt={title} onClick={goToDetails}/>
          </div>
    </div>    
    );
  }


