import React from 'react'
import Banners from  './banners.json'
import Content from './Content'
import './BannersPreview.css'

export default function BannersPreview () {
    return(
    <div>
        <h5 className = "contenidos">Contenidos</h5>
        <div className = "banners">
            { Banners.map(banner => (<Content banner = {banner} key = {banner.id}/>))}
        </div>
    </div>
    )
}

