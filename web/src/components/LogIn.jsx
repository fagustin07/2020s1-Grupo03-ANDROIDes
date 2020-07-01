import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Navigation from './Navigation';
import Api from './Api'



export default function LogIn() {
    const [user, setUser] = useState('');
    const [password, setPassword]= useState('');
    const [error, setError] = useState('');
    const history = useHistory();

    const handleSubmit = (ev) =>{
        ev.preventDefault();
        Api.logIn(user, password)
            .then(response => {
                if(response.status===200) {
                    localStorage.setItem('auth',response.headers.authorization);
                    history.push('/main');
                }
            })
            .catch(response => console.log(response.error));

        setError('Invalid email or password, please, try again.');
    }

    return(
        <>
        <Navigation isLogged={false} />
        <div className = "logoContainer"><h1 className="text-dark">WELCOME TO <span className = "logo">UNQFLIX!</span></h1></div>
        <form className="card col-auto m-5 p-3 " onSubmit={handleSubmit}>
            <h1>Log in!</h1>
            <div>
                <label htmlFor="username">Username</label>
                <input type="text"
                    value={user}
                    className="form-control"
                    autoComplete="current-username"
                    placeholder="Username"
                    onChange={(ev) => {
                        setUser(ev.target.value);
                        setError('')}}  />
            </div>
            <div>
                <label htmlFor="password">Password</label>
                <input type="password"
                    value={password}
                    className="form-control"
                    placeholder="Password"
                    autoComplete="current-password"
                    onChange={(ev) => {
                        setPassword(ev.target.value);
                        setError('')}}  />  
            </div>
                <Link to='/register'> Unregister Yet?</Link>
                {error && <small className="font-weight-bolder alert alert-danger">{error}</small>}
            <div className="text-center">
                <button className="btn btn-info m-3" onSubmit={handleSubmit}> Log in! <img src="iniciar-sesion.png" alt="log in"/></button>
            </div>
        </form>
    </>
    );
}
