import React from 'react';
import Navigation from './Navigation';
import { useState, useEffect } from 'react';
import API from './Api';
import Content from './Content';
import Seasons from './Seasons';
import PlayMovie from './PlayMovie';

export default function Details({match}) {
    const [content,setContent] = useState(undefined);
    const id = match.params.contentId;

    useEffect(() => {
      API.getDetails(id)
      .then(response => setContent(response.data))
      .catch(() => console.log('Boom'));
    },[id])

    return (
      <div className = "container">
      <Navigation isLogged={true}/>
      {content && !content.status && <h1>Sorry! {content.title} is Unavailable :(</h1>}
      { content && content.status && 
        <>
            <div className="wrap">
              <div className="">
                <img src={content.poster} alt="foto" className='left' />
              </div>
              <div>
                <h1 className='right text-capitalize'> {content.title} </h1>
                <p className='right text-justify '>{content.description}</p>
                {content.id.includes('mov') && <PlayMovie movie={content}/>}
                {!content.id.includes('mov') && <Seasons seasons={content.seasons}/>}
              </div>
            </div>
              <div className="wrap ">
              <h5 > Related Content </h5>
              <div className = "banners">
                {content.relatedContent.map( banner => (<Content key={banner.id} banner = {banner}/>))}
            </div>
            
            </div>
            </>}
        </div>
    );
  }