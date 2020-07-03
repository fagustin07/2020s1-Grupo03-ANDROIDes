import React, {useState} from 'react';
import Navigation from './Navigation';
import {useHistory} from 'react-router-dom';
import API from './Api';

const nameRegExp = RegExp(/^[a-zA-Z]+(\s+[a-zA-Z]+)['`-]*$/);
const emailRegExp = RegExp(/^[^@]+@[^@]+\.[a-zA-Z]{2,}$/);
const passwordRegExp = RegExp(/^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{6,16}$/);
const creditCardRegExp = RegExp (/^([\d{4}\s]){19}$/);
const imageRegExp = RegExp(/^(http(s?):)([/|.|\w|\s|-])*\.(?:jpg|gif|png)$/);

export default function Register() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [repeatedPass, setRepeatedPass] = useState('');
    const [image, setImage] = useState('');
    const [creditCard, setCreditCard] = useState('');
    const [password, setPassword]= useState('');
    const [error, setError] = useState('');

    const history = useHistory();

    const handleSubmit = event => {
        event.preventDefault();
        API.register(name, email, password, repeatedPass, image, creditCard)
            .then(response=>{
                if(response.status>=200 && response.status<300){
                   history.push('/');
                }
            })
            .catch(error =>
                {
                    setError('Any field is invalid, check ours messages. If all fields are completed, the email inserted is now register');
                });
    };

    return(
        <>
        <Navigation isLogged={false} />
        <form className="formulario card m-5 p-3 bg-light " onSubmit={handleSubmit}>
            <h1 className="text text-light bg-dark rounded">Register Now!</h1>
            <div className="form-group">
                <input type="text"
                    className="form-control"
                    value={name}
                    placeholder="Name and Surname"
                    onChange={(ev) => {setName(ev.target.value); setError('')}} />
                    {!nameRegExp.test(name) && <small className="text-danger">Insert your name and surname.</small> }
                    {nameRegExp.test(name) && <small className="text-success">Nice data :)</small>}
            </div>
            <div className="form-group">
                <input type="email"
                    value={email}
                    className="form-control"
                    autoComplete="current-mail"
                    placeholder="Email"
                    onChange={(ev) => {setEmail(ev.target.value); setError('')}} />
                    {!emailRegExp.test(email) &&
                    <small className="text-danger">Insert and valid email to log in the app.</small> }
                    {emailRegExp.test(email) && <small className="text-success">Nice data :)</small>}
            </div>

            <div className="form-group">
                <input type="text"
                    value={image}
                    className="form-control"
                    placeholder="URL image for your avatar"
                    onChange={(ev) => {setImage(ev.target.value); setError('')}} />
                    {!imageRegExp.test(image) &&
                    <small className="text-danger">Here, put an URL to your avatar image. Starting
                    with http:// or https://, and ending with .png, .gif or .jpg</small>}
                    {imageRegExp.test(image) && <small className="text-success">Nice data :)</small>}
           </div>
            <div className="form-group">
                <input type="creditCard"
                    value={creditCard}
                    className="form-control"
                    placeholder="Credit card number"
                    onChange={(ev) => {setCreditCard(ev.target.value); setError('')} } />
                    {!creditCardRegExp.test(creditCard) &&
                    <small className="text-danger">Insert your credit card with format: XXXX XXXX XXXX XXXX.</small>}
                    {creditCardRegExp.test(creditCard) && <small className="text-success">Nice data :)</small>}
            </div>

            <div className="form-group">
                <input type="password"
                    value={password}
                    className="form-control"
                    autoComplete="new-password"
                    placeholder="Password"
                    onChange={(ev) => {setPassword(ev.target.value); setError('')}} />
                    {!passwordRegExp.test(password) &&
                    <small className="text-danger">For security reasons, your password must be between 6 and 16 characters
                    and contains at least one capital letter and one number.</small>}
                    {passwordRegExp.test(password) && <small className="text-success">Nice data :)</small>}
            </div>
            <div className="form-group">
                <input type="password"
                    className="form-control"
                    value={repeatedPass}
                    autoComplete="new-password"
                    placeholder="Repeat password"
                    onChange={(ev) => {setRepeatedPass(ev.target.value); setError('')}} />
                    {(password!==repeatedPass || repeatedPass==='') &&
                    <small className="text-danger">Please, repeat inserted password.</small>}
                    {password===repeatedPass && repeatedPass!=='' && <small className="text-success">Nice data :)</small>}
            </div>

            <div className="text-center">
                <button className="btn btn-info" onSubmit={handleSubmit}><img src={require("../images/registro.png")} alt="registrar"/> Start now! </button>
                {error && <small className="font-weight-bolder alert alert-danger d-block mt-3">{error}</small>}
            </div>
        </form>
        </>
    );
}