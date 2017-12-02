import React from'react';
import request from'superagent';

const url='http://localhost:2222/tasks/';

class Client extends React.Component{
    state={tasks:[],taskName:''};

    componentDidMount(){
        this.getAllTasks()
    }

    getAllTasks(){
        request
            .get(url)
            .end((err,res)=>{
                this.setState({tasks:res.body});
            });
    }

    insertTask=(event)=>{
        event.preventDefault();
        request
            .post(url)
            .send({name:this.state.taskName})
            .end((err,res)=>{
                this.setState({taskName:''});
                this.getAllTasks();
            });
    }

    deleteTask(id){
        request
            .delete(url+id)
            .end((err,res)=>{
                this.getAllTasks()
            });
    }

    render(){
        return(
            <div>
                <div>
                    <form onSubmit={this.insertTask}>
                    <input type="text"
                        value={this.state.taskName}
                        onChange={(event)=>this.setState({taskName:event.target.value})}
                        placeholder="NewTask" required/>
                    <button type="submit">AddTask</button>
                    </form>
                </div>
            <div>
                <ul>
                    {this.state.tasks.map(task=>
                        <li key={task.id}>
                            {task.name}
                            <button onClick={()=>{this.deleteTask(task.id)}}>delete</button>
                        </li>
                    )}
                </ul>
            </div>
        </div>
        );
    }
}

export default Client;

