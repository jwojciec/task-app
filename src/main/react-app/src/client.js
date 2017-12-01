import React from 'react';
import request from 'superagent';

class Client extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      tasks: []
    };
  }

  componentDidMount() {
    request
        .get('http://localhost:2222/tasks')
        .end((err, res) => {
            const tasks = res.body;
            this.setState({tasks});
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

