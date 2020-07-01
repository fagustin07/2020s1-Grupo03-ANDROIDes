import React  from 'react';
import { useHistory } from 'react-router';

export default function Navigation({isLogged}) {
  const history = useHistory();
  
  const goToHome = () => {
    isLogged && history.push('/main');
    !isLogged && history.push('/');
  }
  
  const handleLogOut = () => {
    history.push('/');
    localStorage.clear();
  }

  const goToContent = () =>history.push('/content');
  
  const goToSearch = () => history.push('/search');


  return (
      <nav className="navbar navbar-dark bg-secondary p-10 justify-content-between">
      { isLogged &&
            <>
          <button type="button" className="navbar-brand btn btn-info" onClick={goToHome}><img src="casa.png" alt="home"/></button>
          <button type = "button" className = "navbar-brand btn btn-info" onClick = {goToContent}>Contenido</button>
          <h1 className = "logo" onClick = {goToHome}>UNQFLIX</h1>
          <button className="btn btn-outline-light my-2 my-sm-0" type="submit" onClick={goToSearch}><img src="buscar.png" alt="buscador"/></button>
          <button className="btn btn-danger my-2 my-sm-0" type="submit" onClick={handleLogOut}><img src="salida.png" alt="log out"/></button>
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