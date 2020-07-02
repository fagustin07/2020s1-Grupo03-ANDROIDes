import React, { useState } from 'react';
import Content from './Content'
import Carousel from 'react-multi-carousel';
import 'react-multi-carousel/lib/styles.css';
import { useEffect } from 'react';
import API from './Api';

const responsive = {
    desktop: {
      breakpoint: {
        max: 3000,
        min: 1024
      },
      items: 3,
      slidesToSlide: 2,
      partialVisibilityGutter: 40
    },
    mobile: {
      breakpoint: {
        max: 464,
        min: 0
      },
      items: 2,
      slidesToSlide: 2,
      partialVisibilityGutter: 30
    },
    tablet: {
      breakpoint: {
        max: 1024,
        min: 200
      },
      items: 1,
      slidesToSlide: 1,
      partialVisibilityGutter: 30
    }
  };
export default function MainCarrusel(){
    const [content, setContent]= useState([]);

    useEffect(()=>{
        API.getContent()
        .then(response => setContent(response.data))
        .catch(error => console.log(error))
    },[]);


return(
<Carousel
    additionalTransfrom={0}
    arrows = {false}
    autoPlay
    autoPlaySpeed={6000}
    centerMode={false}
    className=""
    containerClass="container"
    dotListClass=""
    draggable
    focusOnSelect={false}
    infinite
    itemClass=""
    minimumTouchDrag={80}
    renderButtonGroupOutside
    renderDotsOutside={false}
    responsive = {responsive}
    showDots={false}
    sliderClass=""
    slidesToSlide={1}

>
 {content.map(banner =><Content banner = {banner} titleApear = {false} key = {banner.id}/>)} 
</Carousel>)
}