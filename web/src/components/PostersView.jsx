import React from 'react'
import Content from './Content'
import './PostersView.css'

export default function PostersView ({ content, text }) {
    return(
    <div>
    <h5 className = "text" >{text}</h5>
        <pre className = "blockDisplay">
            <div className = "banners">
                { content.map(banner => (<Content banner = {banner} key = {banner.id}/>))}
            </div>
        </pre> 
    </div>
    )
}