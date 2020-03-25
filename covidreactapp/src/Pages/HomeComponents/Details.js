import React from 'react';

function Details(props) {
    console.log(props)
    return (
  <article class="media">
    <div class="media-left">
      <figure class="image is-64x64">
        <img src="https://lh3.googleusercontent.com/-aRoy2dv-oyU/AAAAAAAAAAI/AAAAAAAAAAA/AAKWJJO_i8Uh-v2LEFf7P2RMoQazKlNVKw/photo.jpg?sz=80" alt="Image" />
      </figure>
    </div>
    <div class="media-content">
      <div class="content">
        <p>
          <strong>{props.data.name}</strong> <small>@props.data.name</small> <small>31m</small>
          <br />
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean efficitur sit amet massa fringilla egestas. Nullam condimentum luctus turpis.
        </p>
      </div>
      
    </div>
  </article>
    )
}

export default Details;
