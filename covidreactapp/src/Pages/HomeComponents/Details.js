import React from 'react';

function Details(props) {
    console.log(props)
    return (
  <article class="media" style={{boxShadow:"0 0 2px grey",borderRadius:"5px",padding:'10px'}}>
    <div class="media-left">
      <figure class="image is-64x64 waves-effect" style={{borderRadius:'50%'}}>
        <img src="https://lh3.googleusercontent.com/-aRoy2dv-oyU/AAAAAAAAAAI/AAAAAAAAAAA/AAKWJJO_i8Uh-v2LEFf7P2RMoQazKlNVKw/photo.jpg?sz=64" alt="Image" />
      </figure>
    </div>
    <div class="media-content">
      <div class="content">
        <p>
          <strong className='waves-effect'>{props.data.name}</strong> <small className='waves-effect'>@props.data.name</small> <small className='waves-effect'>31m</small>
          <br />
          <button className='button is-primary is-small  waves-effect 'style={{margin:'20px',width:'80%'}}>Edit Profile</button>
        </p>
      </div>
      
    </div>
  </article>
    )
}

export default Details;
