import React from 'react';
import axios from 'axios';

class Client extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      tasks: []
    };
  }

  componentDidMount() {
    axios.get('http://localhost:2222/tasks')
      .then(res => {
        const tasks = res.data;
        this.setState({ tasks });
      });
  }

  render() {
    return (
      <div>
        <ul>
          {this.state.tasks.map(task =>
            <li key={task.id}>{task.name}</li>
          )}
        </ul>
      </div>
    );
  }
}

export default Client;

