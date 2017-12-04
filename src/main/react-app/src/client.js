import React from'react';
import request from'superagent';

const url = (process.env.NODE_ENV === 'development') 
        ? 'http://localhost:2222/tasks/'
        : 'https://calm-wave-83699.herokuapp.com/tasks/';

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
            <fieldset class="tasks-list">
                <div>
                    <form class="tasks-form" onSubmit={this.insertTask}>
                    <input class="tasks-input" type="text"
                        value={this.state.taskName}
                        onChange={(event)=>this.setState({taskName:event.target.value})}
                        placeholder="New task" required/>
                    <button class="tasks-button" type="submit">Add Task</button>
                    </form>
                </div>
            <div>
                    {this.state.tasks.map(task=>
                        <label class="tasks-list-item">
                            <input type="checkbox" name="task_2" value="1" class="tasks-list-cb" unchecked />
                            <span class="tasks-list-mark"></span>
                            <span class="tasks-list-desc">{task.name}</span>
                        </label>                            
                    )}
            </div>
            </fieldset>
        );
    }
}

export default Client;

