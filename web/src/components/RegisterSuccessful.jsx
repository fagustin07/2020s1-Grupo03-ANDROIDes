import React from 'react';
import { useHistory } from 'react-router-dom';

export default function RegisterSuccesfull({match}){
    const history = useHistory();
    const {name, email} = match.params;

    const goToHome= () => history.push('/');
    return (
        <div className="container">
            <div className="container bg-secondary col rounded mt-5 p-2 text-center">
                <h1 className="text-light">Registration successful!</h1>
                <p className="text-light">Congratulations {name}, you are now register in Unqflix.</p>
                <p className="text-light">Your email to log in is: {email}</p>
                <button className="btn btn-success m-4" onClick={goToHome}>Enjoy!</button>
            </div>
        </div>
    )
}