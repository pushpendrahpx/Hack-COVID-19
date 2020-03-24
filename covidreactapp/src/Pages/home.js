import React, { Component } from 'react'
import './home.css';
class home extends Component {
    constructor(props) {
        super(props)

        this.state = {
                 
        }
    }
 

    render() {
        return (
            <div
            >
                
                <article class="message is-primary">
                <div class="message-header">
                    <p>Your Account Details</p>
                    <button class="delete" aria-label="delete"></button>
                </div>
                <div class="message-body">

                </div>
                </article>
<article class="panel is-primary">
  <p class="panel-heading">
    Primary
  </p>
  <p class="panel-tabs">
    <a class="is-active">All</a>
    <a>Public</a>
    <a>Private</a>
    <a>Sources</a>
    <a>Forks</a>
  </p>
  <div class="panel-block">
    <p class="control has-icons-left">
      <input class="input is-primary" type="text" placeholder="Search" />
      <span class="icon is-left">
        <i class="fas fa-search" aria-hidden="true"></i>
      </span>
    </p>
  </div>
  <a class="panel-block is-active">
    <span class="panel-icon">
      <i class="fas fa-book" aria-hidden="true"></i>
    </span>
    bulma
  </a>
  <a class="panel-block">
    <span class="panel-icon">
      <i class="fas fa-book" aria-hidden="true"></i>
    </span>
    marksheet
  </a>
  <a class="panel-block">
    <span class="panel-icon">
      <i class="fas fa-book" aria-hidden="true"></i>
    </span>
    minireset.css
  </a>
  <a class="panel-block">
    <span class="panel-icon">
      <i class="fas fa-book" aria-hidden="true"></i>
    </span>
    jgthms.github.io
  </a>
</article>
            </div>
        )
    }
}

export default home
