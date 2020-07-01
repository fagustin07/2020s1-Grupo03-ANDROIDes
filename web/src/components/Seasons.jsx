import React, { useState } from 'react';
import {Card, Accordion, Button, ListGroup, Modal} from 'react-bootstrap';

const List = ({season, setSelectedChapter, handleShow}) => {
  return (
    <ListGroup>
    {season.chapters.map((chapter,i) =>
         <ListGroup.Item  key={chapter.id}
         className="capitulo bg-dark text-light text-capitalize"
         onClick={() => {
             setSelectedChapter(chapter);
             handleShow();
         }
             }>
             {`${i}.${chapter.title}`}
         </ListGroup.Item>
     )}
    </ListGroup>
  )
}

const Seasons = ({seasons}) => {
    const [selectedChapter, setSelectedChapter] = useState(undefined);
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
        <Accordion defaultActiveKey="0">
        {seasons.map((season, i) =>
             <Card key={season.id} className="bg-dark">
             <Card.Header>
               <Accordion.Toggle as={Button} className="text-capitalize" variant="link" eventKey={i}>
                 {season.title}
               </Accordion.Toggle>
             </Card.Header>
             <Accordion.Collapse eventKey={i}>
               <Card.Body>
                  <List season={season} setSelectedChapter={setSelectedChapter} handleShow={handleShow}/>
               </Card.Body>
             </Accordion.Collapse>
           </Card>
        )}
       </Accordion>
       {selectedChapter &&
       <Modal show={show} onHide={handleClose}
       size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered>
        <Modal.Header closeButton>
          <Modal.Title className="text-capitalize">{selectedChapter.title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <iframe src={selectedChapter.video}
         width="640" height="360" frameborder="0" allowFullScreen></iframe>
        </Modal.Body>
      </Modal>}
    </>
    )
}

export default Seasons;