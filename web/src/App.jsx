import React from 'react';
import './App.css';
import {BrowserRouter, Route, Switch } from 'react-router-dom';
import Home from './components/Home';
import 'bootstrap/dist/css/bootstrap.min.css';
import RegisterForm from './components/RegisterForm';
import LogIn from './components/LogIn';
import NotFound from './components/NotFound';
import ModalSearch from './components/Modal';

function App() {
  return (
    <div className= "home">
      <BrowserRouter>
        <Switch>
            <Route path="/register" component={RegisterForm}/>
            <Route path="/home" component={Home}/>
            <Route exact path="/" component={LogIn} />
            <Route path = '/search' component={ModalSearch}/>
            <Route path="*" component={NotFound} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
