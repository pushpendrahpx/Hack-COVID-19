import {Map, InfoWindow, Marker, GoogleApiWrapper} from 'google-maps-react';
import React,{Component} from 'react'
export class MapContainer extends Component {
    constructor(props){
        super(props)

        console.log(this.props)
    }
  render() {
    return (
      <Map
      
      google={this.props.google}
      
      initialCenter={{
        lat: this.props.latitude,
        lng: this.props.longitude
      }}
      zoom={16}
      onClick={this.onMapClicked}
      
      
      >

        <Marker onClick={this.onMarkerClick}
                name={'Current location'} />

        <InfoWindow onClose={this.onInfoWindowClose}>
            <div>
              {/* <h1>{this.state.selectedPlace.name}</h1> */}
            </div>
        </InfoWindow>
      </Map>
    );
  }
}

export default GoogleApiWrapper({
  apiKey: ("AIzaSyBfrIvC8g62dfdbIKzBK_zbkGBlyVmwzM4")
})(MapContainer)