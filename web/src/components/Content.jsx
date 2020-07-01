import React from 'react';
import { useHistory } from 'react-router';
import API from './Api'
import './Content.css';

export default function Content({ banner }) {

    const { poster, title, id } = banner;
    const history = useHistory();

    const goToDetails = () => {
        API.getDetails(id)
            .then(response => {
              console.log(response.data)
               history.push('/content/:contentId', response.data)
              })
            .catch(() => console.log('Boom'));
    }


    // const goToDetails2 = () => {
    //     API.getContent()
    //     .then(response => {
    //       console.log(response.data)
    //       //  history.push('/content', response.data)
    //       })
    //     .catch(() => console.log('Boom'));
    //   }

    return (
    <div className = "contentContainer">
          <div className = "p-3 contenido">
            <img className = 'poster' src = {poster} alt={title} onClick={goToDetails}/>
            <h5 className = 'text-light text-capitalize title' onClick={goToDetails}>{title}</h5>
          </div>
    </div>    
    );
  }


