import React, { useState } from 'react';
import {Card, Accordion, Button, ListGroup} from 'react-bootstrap';
import ModalPlayVideo from './ModalPlayVideo';

const List = ({season, setSelectedChapter, handleShow}) => {
  return (
    <ListGroup>
    {season.chapters.map((chapter,i) =>
         <ListGroup.Item  key={chapter.id}
         className="capitulo bg-secondary text-light text-capitalize"
         onClick={() => {
             setSelectedChapter(chapter);
             handleShow();
         }
             }>
             {`${i}. ${chapter.title}`}
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
             <Card key={season.id} className="bg-dark m-3">
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
       {selectedChapter && <ModalPlayVideo content={selectedChapter} show={show} handleClose={handleClose}/>}
    </>
    )
}

export default Seasons;