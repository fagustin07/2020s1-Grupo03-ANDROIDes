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
        // const findedUser = users.find(anUser => anUser.user===user && anUser.password===password);
        Api.logIn(user, password)
        .then(response => history.push('/home', {...response.headers}))
        .catch(response => console.log(response.error));
        // if(findedUser){
        //     history.push('/home', {...findedUser});
        // }
        // setError('Invalid username or password, please, try again.')
        // setPassword('');
    }

    return(
        <>
        <Navigation isLogged={false} />
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
                <button className="btn btn-info m-3" onSubmit={handleSubmit}>Log in!</button>
            </div>
        </form>
    </>
    );
}
