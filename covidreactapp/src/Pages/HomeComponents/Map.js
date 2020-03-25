import React, { Component } from 'react';
import { GoogleMap,withScriptjs,withGoogleMap, Marker, Circle } from 'react-google-maps'

class Map extends Component {
    constructor(props){
        super(props)

        console.log(props)

        
    } 

    render(){
        return (
            <GoogleMap
                defaultZoom={22}
                defaultCenter={{lat:22.2267857,lng:73.1868027}} 
            >
                <Marker
                    position={{lat:22.2267857,lng:73.1868027}}            
                />
                <Circle center={{lat:22.2267857,lng:73.1868027}} radius={6} />
            </GoogleMap>
        )
    }
}

const WrappedMap = withScriptjs(withGoogleMap(Map))

export default function(props){
    return <center style={{height:'600px',margin:'30px'}}><WrappedMap
    googleMapURL={`https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places`} 
    loadingElement={<div style={{height:'100%'}} />}
    containerElement={<div style={{ height:'100%',width:'100%' }} />}
    mapElement={<div style={{ height: `100%` }} />}   
    />
    
    
    </center>
};
