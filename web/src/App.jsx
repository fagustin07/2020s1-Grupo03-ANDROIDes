import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import RegisterForm from './components/RegisterForm';
import ModalSearch from './components/Modal';
import NotFound from './components/NotFound';
import Details from './components/Details';
import LogIn from './components/LogIn';
import Home from './components/Home';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {

  return (
    <div className= "home">
      <BrowserRouter>
        <Switch>
            <Route exact path="/" component={LogIn} />
            <Route path="/register" component={RegisterForm}/>
            <Route path="/home" component={Home}/>
            <Route path = '/search' component={ModalSearch}/>
            <Route exact path = '/content' component={Details}/>
            <Route path = '/content/:contentId' component={Details}/>
            <Route path="*" component={NotFound} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
