import React, { useState }  from 'react'
import { useHistory } from 'react-router';
import API from './Api'
import { getAllByAltText } from '@testing-library/react';



export default function SearchBar({ searchBarMode }){   
    const history = useHistory();
    const [text, setText]= useState('');

    const handleSubmit = () => {
        API.searchContent(`/search?text=${text}`)
        .then(response => {
           history.push('/search', response.data)
          })
        .catch(() => console.log('Boom'));
      }
    
    return( 
        <div className="form-inline text-center">
            { searchBarMode && 
            <>
             <input
              className="form-control m-2 col"
              placeholder="Insert title here..."
              type="text"
              onChange={event => 
                              { setText(event.target.value)
                                if(text === '') {} 
                                else {handleSubmit()} }
                              }
            /> 
             <button className="btn btn-outline-light my-2 my-sm-0" type="submit" onClick={handleSubmit}>Search <img src="buscar.png"/></button>
            </>
            }
        </div>
    );

 }

