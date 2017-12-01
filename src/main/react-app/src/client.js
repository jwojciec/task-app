import React from 'react';
import ReactDOM from 'react-dom';

class Client extends React.Component {
  render() {
    return <h3>Hello From Client</h3>;
  }
}

ReactDOM.render(<Client />, document.getElementById('client'));

export default Client;

