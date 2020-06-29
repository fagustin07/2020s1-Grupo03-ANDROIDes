import axios from 'axios'

const url = 'http://localhost:7342'

const logIn = (email , password) => axios.post(url + '/login', {  email: email, password: password });

const getUser = (authorization) => axios.get(url + '/user', { headers : { Authorization: authorization } });

const searchContent = (authorization, path) => axios.get(url + path, {headers : {Authorization : authorization}});

const getContent = (authorization) => axios.get (url + '/content', { headers : {Authorization : authorization}});

export default {
    getContent,
    searchContent,
    getUser,
    logIn,

};