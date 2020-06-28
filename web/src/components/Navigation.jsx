import React, { useState } from 'react';
import { useHistory } from 'react-router';
import {Link } from 'react-router-dom';
export default function Navigation({isLogged}) {
  let history= useHistory();
  const [text, setText]= useState('');

  const handleSubmit = () => {
    
    history.push(`/search/${text}`);
  }

  const goToHome = () => {
    history.push('/');
  }
  return (
      <nav className="navbar navbar-dark bg-secondary p-10 justify-content-between">
      { isLogged &&
            <>
          <button type="button" className="navbar-brand btn btn-info" onClick={goToHome}> Home<span className="oi oi-book" /> </button>
          <div className="form-inline text-center">
            <input
              className="form-control m-2 col"
              placeholder="Insert title here..."
              type="text"
              onChange={event => setText(event.target.value)}
            />
            <button className="btn btn-outline-light my-2 my-sm-0" type="submit" onClick={handleSubmit}>Search</button>
          </div>
          <Link to="/" className="btn btn-danger">Log Out!</Link>
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