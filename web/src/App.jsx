import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import RegisterForm from './components/RegisterForm';
import NotFound from './components/NotFound';
import Details from './components/ContentView/Details';
import LogIn from './components/LogIn';
import Home from './components/Home/Home';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import SearchBar from './components/SearchBar';

function App() {

  return (
    <div className= "home">
      <BrowserRouter>
        <Switch>
            <Route exact path="/" component={LogIn} />
            <Route path="/register" component={RegisterForm}/>
            <Route path="/main" component={Home}/>
            <Route path = '/search' component={SearchBar}/>
            <Route path = '/content/:contentId' component={Details}/>
            <Route path="*" component={NotFound} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
