import React from 'react'
import Content from './Content'
import Carousel from 'react-multi-carousel';
import 'react-multi-carousel/lib/styles.css';

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
export default function BannersCarrusel({ content,text }){
 return(
   <div className ="contentContainer">
     <h5 className = "text">{text}</h5> 
  <Carousel
    additionalTransfrom={0}
    arrows
    autoPlaySpeed={3000}
    centerMode={false}
    className=""
    containerClass="container"
    dotListClass=""
    draggable
    focusOnSelect={false}
    infinite
    itemClass=""
    keyBoardControl
    minimumTouchDrag={80}
    renderButtonGroupOutside={false}
    renderDotsOutside={false}
    responsive = {responsive}
    showDots={false}
    sliderClass=""
    slidesToSlide={1}
    swipeable
    >   
      {content.map(banner => <Content banner = {banner} titleApear = {true} key = {banner.id}/> )} 
  </Carousel>
  </div>)
};


