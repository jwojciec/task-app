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

  updateTask(task) {
        request
            .post(url)
            .send({ id: task.id, name: task.name, active: !task.active })
            .end((err,res)=>{
                this.getAllTasks()
            });          
  }        

    render(){
        return(
            <fieldset className="tasks-list">
                <div>
                    <form className="tasks-form" onSubmit={this.insertTask}>
                    <input className="tasks-input" type="text"
                        value={this.state.taskName}
                        onChange={(event)=>this.setState({taskName:event.target.value})}
                        placeholder="New task" required/>
                    <button className="tasks-button" type="submit">Add Task</button>
                    </form>
                </div>
                <div>
                    {this.state.tasks.map(task=>
                        <label className="tasks-list-item" key={task.id}>
                            <input type="checkbox" className="tasks-list-cb" onChange={()=>{this.updateTask(task)}} checked={!task.active} />
                            <span className="tasks-list-mark"></span>
                            <span className="tasks-list-desc">{task.name}</span>
                            <button className="tasks-delete" onClick={()=>{this.deleteTask(task.id)}}>delete</button>
                        </label>                            
                    )}
                </div>
            </fieldset>
        );
    }
}

export default Client;

