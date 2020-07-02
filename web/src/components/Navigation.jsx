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

  const goToSearch = () => history.push('/search');


  return (
      <nav className="navbar navbar-dark bg-secondary row p-10 justify-content-between rounded">
      { isLogged &&
            <>
          <h1 className = "logo col-1" onClick = {goToHome}>UNQFLIX</h1>
          <span className="col-8"/>
          <button className="btn btn-outline-light col-1 mr-2" type="submit" onClick={goToSearch}><img src={require("../images/buscar.png")} alt="buscador"/></button>
          <button className="btn btn-danger my-2 my-sm-0 col-1" type="submit" onClick={handleLogOut}><img src={require("../images/salida.png")} alt="log out"/></button>
        </>
    }
    { !isLogged &&
    <>
      <button type="button" className="navbar-brand btn btn-info" onClick={goToHome}><img src={require("../images/casa.png")} alt="home"/></button>
      <h1 className="text-light">Hello!</h1>
    </>
    }
      </nav>
    );
  }