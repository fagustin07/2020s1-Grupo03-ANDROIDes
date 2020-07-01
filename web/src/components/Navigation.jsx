import React, { useState } from 'react';
import { useHistory } from 'react-router';
import API from './Api'

export default function Navigation({isLogged}) {
  const history = useHistory();
  const [text, setText]= useState('');

  const handleSubmit = () => {
    API.searchContent(`/search?text=${text}`)
    .then(response => {
       history.push('/search', response.data)
      })
    .catch(() => console.log('Boom'));
  }

  const goToHome = () => {
    isLogged && history.push('/home');
    !isLogged && history.push('/');
  }

  const handleLogOut = () => {
    history.push('/');
    localStorage.setItem('auth',undefined);
  }

  return (
      <nav className="navbar navbar-dark bg-secondary p-10 justify-content-between">
      { isLogged &&
            <>
          <button type="button" className="navbar-brand btn btn-info" onClick={goToHome}><img src="casa.png"/> Home <span className="oi oi-book" /> </button>
          <div className="form-inline text-center">
            <input
              className="form-control m-2 col"
              placeholder="Insert title here..."
              type="text"
              onChange={event => setText(event.target.value)}
            />
            <button className="btn btn-outline-light my-2 my-sm-0" type="submit" onClick={handleSubmit}>Search <img src="buscar.png"/></button>
          </div>
          <button className="btn btn-danger my-2 my-sm-0" type="submit" onClick={handleLogOut}> Log Out! <img src="salida.png"/></button>
        </>
    }
    { !isLogged &&
    <>
      <button type="button" className="navbar-brand btn btn-info" onClick={goToHome}> Home<span className="oi oi-book" /> </button>
      <h1 className="text-light">WELCOME TO UNQFLIX!</h1>
    </>
    }
      </nav>
    );
  }