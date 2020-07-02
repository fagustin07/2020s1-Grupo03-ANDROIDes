import React from 'react';
import { useHistory } from 'react-router';

export default function Content({ banner, titleApear }) {

    const { poster, title, id } = banner;
    const history = useHistory();

    const goToDetails = () => history.push(`/content/${id}`)

    return (
    <div className = "contentContainer">
          <div className = "p-4 contenido">
            <img className = 'poster' src = {poster} alt={title} onClick={goToDetails}/>
            {titleApear &&
            <>
            <h3 className = "title" onClick = {goToDetails}>{title.toUpperCase()}</h3>
            </>
            }
            </div>
    </div>    
    );
  }


