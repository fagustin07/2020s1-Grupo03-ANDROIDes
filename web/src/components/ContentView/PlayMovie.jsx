import React, { useState } from 'react';
import ModalPlayVideo from './ModalPlayVideo';

const PlayMovie = ({movie}) => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
  
      return (
          <>
          <button type="button" 
            className="btn btn-info"
            onClick={handleShow} >
          <img src={require("../../images/video.png")} alt="play video"/> Play! </button>
          {console.log(movie)}

         <ModalPlayVideo content={movie} show={show} handleClose={handleClose}/>
      </>
      )
}

export default PlayMovie;