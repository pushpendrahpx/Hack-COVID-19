import React, { Component } from 'react';
import { GoogleMap,withScriptjs,withGoogleMap, Marker, Circle } from 'react-google-maps'

class Map extends Component {
    constructor(props){
        super(props)
        // console.log(tempData)
        let tempData = (JSON.parse(localStorage.getItem("userData")));
        console.log(tempData)
        tempData = [(tempData.location.latitude.value),(tempData.location.longitude.value)]
        console.log(tempData)
        this.lat = tempData[0];
        this.lng = tempData[1];


        this.state = {
            lat:this.lat,
            lng:this.lng
        }
    } 

    render(){
        return (
            <GoogleMap
                defaultZoom={22}
                defaultCenter={{lat:this.state.lat,lng:this.state.lng}} 
            >
                <Marker
                    position={{lat:this.state.lat,lng:this.state.lng}}            
                />
                <Circle center={{lat:this.state.lat,lng:this.state.lng}} radius={6} />
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
