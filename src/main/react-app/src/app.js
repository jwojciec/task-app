import React from 'react';
import ReactDOM from 'react-dom';

class App extends React.Component {
  render() {
    return <h3>Hello From React</h3>;
  }
}

let target = document.getElementById('root');

ReactDOM.render(<App/>, target);

export default App;

