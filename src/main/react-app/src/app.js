import React from 'react';

class App extends React.Component {
  render() {
    return (
        <header className="tasks-header">
            <h2 className="tasks-title">Tasks</h2>
            <a href="index.html" className="tasks-lists">Lists</a>
        </header>            
    );
  }
}

export default App;
