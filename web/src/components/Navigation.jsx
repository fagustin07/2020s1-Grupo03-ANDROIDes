import React  from 'react';
import { useHistory } from 'react-router';
import API from './Api'
import SearchBar from './SearchBar';

export default function Navigation({isLogged, searchBarMode}) {
  const history = useHistory();

  const getAll = () => {
    API.getContent()
    .then(response => {
      console.log(response.data)
       history.push('/content', response.data)
      })
    .catch(() => console.log('Boom'));
  }

  const goToHome = () => {
    isLogged && history.push('/main');
    !isLogged && history.push('/');
  }

  const handleLogOut = () => {
    history.push('/');
    localStorage.setItem('auth',undefined);
  }

  const goToContent = () => {
      getAll()
  }


  return (
      <nav className="navbar navbar-dark bg-secondary p-10 justify-content-between">
      { isLogged &&
            <>
          <button type="button" className="navbar-brand btn btn-info" onClick={goToHome}><img src="casa.png"/> Home <span className="oi oi-book" /> </button>
          <button type = "button" className = "navbar-brand btn btn-info" onClick = {goToContent}>Contenido</button>
          <h1 className = "logo" onClick = {goToHome}>UNQFLIX</h1>
          <SearchBar searchBarMode = {searchBarMode}/>
          <button className="btn btn-danger my-2 my-sm-0" type="submit" onClick={handleLogOut}> Log Out! <img src="salida.png"/></button>
        </>
    }
    { !isLogged &&
    <>
      <button type="button" className="navbar-brand btn btn-info" onClick={goToHome}> Home<span className="oi oi-book" /> </button>
      <h1 className="text-light">Hello!</h1>
    </>
    }
      </nav>
    );
  }