import React, { useState, useEffect }  from 'react'
import API from './Api'
import Content from './ContentView/Content';
import Navigation from './Navigation'

export default function Search(){   
    const [text, setText]= useState('');
    const [content, setContent] = useState([]);
    
    useEffect(() => {
        API.searchContent(`/search?text=${text}`)
        .then(response => setContent(response.data))
        .catch(() => console.log('Boom'));
      },[text]);
    
    return( 
          <div className = 'container'>
          <Navigation isLogged = {true}/>   
          <h2 className = "text">Buscador</h2>
            <input
              className="m-2 col"
              placeholder="Insert title here..."
              type="text"
              onChange={event => setText(event.target.value)}
            />
            <div className = "banners">
                {content.map( banner => (<Content key={banner.id} banner = {banner} titleApear= {true}/>))}
            </div>
        </div>
    );

 }

