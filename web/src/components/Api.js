import axios from 'axios'

const url = 'http://localhost:7342'

const logIn = (email , password) =>
    axios.post(url + '/login', {  email: email, password: password });

const getUser = () =>
    axios.get(url + '/user', { headers : { Authorization: localStorage.getItem('auth') } });

const searchContent = (path) =>
    axios.get(url + path, {headers : {Authorization : localStorage.getItem('auth')}});

const getContent = () =>
    axios.get (url + '/content', { headers : {Authorization : localStorage.getItem('auth')}});

const getDetails = (id) =>
    axios.get(url + '/content/' + id , {headers : {Authorization : localStorage.getItem('auth')}});


export default {

    getDetails,
    getContent,
    searchContent,
    getUser,
    logIn,

};