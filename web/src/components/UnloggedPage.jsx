import React from 'react';
import LogInForm from './LogIn';

export default function UnloggedPage({onLogIn}) {
    return(
        <div className="container ">
        <LogInForm onLogIn={onLogIn}/>
        </div>
    );
}