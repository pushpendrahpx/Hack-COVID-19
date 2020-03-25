import React from 'react';
import { GoogleMap,withScriptjs,withGoogleMap } from 'react-google-maps'

function Map() {
    return (
        <GoogleMap
            defaultZoom={10}
            defaultCenter={{lat:45.421532,lng:-75.697189}} 
         />
    )
}

const WrappedMap = withScriptjs(withGoogleMap(Map))

export default function(){
    return <center style={{height:'600px',margin:'30px'}}><WrappedMap
    googleMapURL={`https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places`} 
    loadingElement={<div style={{height:'100%'}} />}
    containerElement={<div style={{ height:'100%',width:'100%' }} />}
    mapElement={<div style={{ height: `100%` }} />}   
    /></center>
};
