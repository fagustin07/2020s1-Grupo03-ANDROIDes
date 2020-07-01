import React, {useState} from 'react';
import {useHistory} from 'react-router-dom';
import Navigation from './Navigation';

// Los regEx usados en el tp de API, hay que ver como encastrarlos.
// const emailRegex = RegExp(/^[^@]+@[^@]+\\.[a-zA-Z]{2,}\$/);
// const nameRegex = RegExp(/^[a-zA-Z]+(\\s+[a-zA-Z]+)['`\\-]*$/);
// const passwordRegex = RegExp(/^(?=.*\\d)(?=.*[A-Z])[\\w!@#$%^&*()_+=\\-{}\\[\\];'|?><,.:\\s]{6,16}\$/);
// const creditCardRegex = RegExp(/^([\\d{4}\\s]){19}$/);
// const imageRegex = RegExp(`/^(https?://)?([\\w\\-])+\\.([a-zA-Z]{2,63})([\\w-]*)*/?\\??([^#\\n\\r]*)?#?([^\\n\\r]+\\.(?:png|jpg|jpeg|gif|svg)*)/`);



export default function LogIn() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [repeatedPass, setRepeatedPass] = useState('');
    const [image, setImage] = useState('');
    const [creditCard, setCreditCard] = useState('');
    const [password, setPassword]= useState('');

    const history = useHistory();

    const formValid = () => {
       return true;
    //   // valida el usuario
    };

    const handleSubmit = event => {
      event.preventDefault();
      
    (formValid) ? 
    /*guardamos el user y redireccion a login*/  
    history.push('/login'):
    /*marcar los errores y limpiar el campo de contraseña*/ 
    console.log('error')    
    };

    return(
        <>
        <Navigation isLogged={false} />
        <form className="card m-5 p-3 bg-light " onSubmit={handleSubmit}>
            <h1 className="text">Register Now!</h1>
            <div className="form-group row">
                <input type="text"
                    className="form-control mx-auto col-5"
                    value={name}
                    placeholder="Name & Surname"
                    onChange={(ev) => setName(ev.target.value)} />

                <input type="email"
                    value={email}
                    className="form-control mx-auto col-5"
                    autoComplete="current-mail"
                    placeholder="Email"
                    onChange={(ev) => setEmail(ev.target.value)} />
            </div>

            <div className="form-group row">
                <input type="url"
                    value={image}
                    className="form-control mx-auto col-5"
                    placeholder="URL image for your avatar"
                    onChange={(ev) => setImage(ev.target.value)} />
           
                <input type="creditCard"
                    value={creditCard}
                    className="form-control mx-auto col-5"
                    placeholder="Credit card number"
                    onChange={(ev) => setCreditCard(ev.target.value) } />
            </div>
            
            <div className="form-group row ">
                <input type="password"
                    value={password}
                    className="form-control mx-auto col-5"
                    autoComplete="new-password"
                    placeholder="Password"
                    onChange={(ev) => setPassword(ev.target.value)} />

                <input type="password"
                    className="form-control mx-auto col-5"
                    value={repeatedPass}
                    autoComplete="new-password"
                    placeholder="Repeat password"
                    onChange={(ev) => setRepeatedPass(ev.target.value)} />

            </div>

            <div className="text-center">
                <button className="btn btn-info" onSubmit={handleSubmit}>Register! <img src="registro.png" alt="registrar"/></button>
            </div>
        </form>
        </>
    );
}