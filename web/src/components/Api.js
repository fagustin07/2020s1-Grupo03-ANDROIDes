import axios from 'axios'

const url = 'http://localhost:7342'

const logIn = (email , password) =>
    axios.post(url + '/login', {  email: email, password: password });

const register = (name, email, password, repeatedPass, image, creditCard) =>
    axios.post(url + '/register', {name: name, password: password, repeatedPass: repeatedPass,
        creditCard: creditCard, image: image, email: email});

const getUser = () =>
    axios.get(url + '/user', { headers : { Authorization: localStorage.getItem('auth') } });

const searchContent = (path) =>
    axios.get(url + path, {headers : {Authorization : localStorage.getItem('auth')}});

const getContent = () =>
    axios.get (url + '/content', { headers : {Authorization : localStorage.getItem('auth')}});

const getDetails = (id) =>
    axios.get(url + '/content/' + id , {headers : {Authorization : localStorage.getItem('auth')}});

const getBanners = () =>
    axios.get(url + '/banners', {headers : {Authorization : localStorage.getItem('auth')}});

export default {
    getDetails,
    getContent,
    searchContent,
    getUser,
    logIn,
    getBanners,
    register,
};