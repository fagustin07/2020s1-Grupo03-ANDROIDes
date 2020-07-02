import React from 'react';
import {Modal} from 'react-bootstrap';


export default function ModalPlayVideo({content, show, handleClose}) {
    return (
        <Modal show={show} onHide={handleClose}
        size="lg"
       aria-labelledby="contained-modal-title-vcenter"
       centered>
         <Modal.Header closeButton className="bg-dark">
           <Modal.Title className="text-capitalize text-light">{content.title}</Modal.Title>
         </Modal.Header>
         <Modal.Body className="bg-dark">
         <iframe title={content.title} src={content.video}
          width="640" height="360" frameborder="0" allowFullScreen></iframe>
         </Modal.Body>
       </Modal>
    )
}