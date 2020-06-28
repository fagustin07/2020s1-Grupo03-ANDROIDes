import axios from 'axios'

const url = 'http://localhost:7342'

const logIn = (email , password) => axios.post(url + '/login', {  email: email, password: password });

const getUser = (authorization) => axios.get(url + '/user', { headers : { Authorization: authorization } });

export default {
    getUser,
    logIn,

};