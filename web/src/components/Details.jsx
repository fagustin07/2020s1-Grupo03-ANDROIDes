import React from 'react';
import { useLocation, useParams } from 'react-router-dom';
import Navigation from './Navigation';
import Content from './Content';
import './Details.css';
import API from './Api'


export default function Details() {

    // const { contentId } = useParams();
    const location = useLocation();
     const content = location.state;
    
     /*
    console.log(props);
    <div><h1> content: {props.match.params.contentId}  </h1> </div>
     */

    return (
        <div className = "container">
            <Navigation isLogged={true}/>
            {/* <div><h1> content: { contentId }  </h1> </div> */}


            <div class="wrap">
              <img src="dbz.jpg" className='left' />
              <h1 className='right'> T I T L E </h1>
              <div className='right'>Content description nnnnnnnnnnnnnnn nnnnnnnnnnn nnnnnnnnnnnnn nnnnnnnnnn nnnnnnnnnn nnnnnnnnnn nnnnnnnn nnnnnn</div>
              <h1 className='right'><button type="button" className="navbar-brand btn btn-info"><img src="video.png"/> Play </button></h1>

            </div> <br/><br/><br/>

            <div class="wrap">
              <h1 > Contenido relacionado </h1>
              <img src="goku.png" className='left' />
              <img src="vegeta.jpg" className='left' />
              <img src="goku.png" className='left' />
              <img src="vegeta.jpg" className='left' />
              <img src="goku.png" className='left' />
              <img src="vegeta.jpg" className='left' />

            </div>



        </div>
    );
  }