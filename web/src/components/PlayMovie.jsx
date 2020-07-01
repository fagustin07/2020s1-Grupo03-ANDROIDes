import React, { useState } from 'react';
import {Modal} from 'react-bootstrap';


const PlayMovie = ({movie}) => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
  
      return (
          <>
          <button type="button" 
            className="btn btn-info">
          <img src="video.png" onClick={handleShow} alt="play video"/></button>
          {console.log(movie)}

         <Modal show={show} onHide={handleClose}
         size="lg"
        aria-labelledby="contained-modal-title-vcenter"
        centered>
          <Modal.Header closeButton>
            <Modal.Title className="text-capitalize">{movie.title}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
          <iframe src={movie.video}
           width="640" height="360" frameborder="0" allowFullScreen></iframe>
          </Modal.Body>
        </Modal>
      </>
      )
}

export default PlayMovie;