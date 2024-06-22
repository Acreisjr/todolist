// src/components/TodoList.js
import React, { useState } from 'react';
import { deleteTask, completeTask } from '../api';

const TodoList = ({ tasks, onEdit, onTaskDeleted }) => {
  const [completedTasks, setCompletedTasks] = useState([]);

  const handleDelete = async (id) => {
    try {
      await deleteTask(id);
      onTaskDeleted();
    } catch (error) {
      console.error('Error deleting task', error);
    }
  };

  const handleComplete = async (id) => {
    try {
      await completeTask(id); 
      setCompletedTasks(prevState => [...prevState, id]);
    } catch (error) {
      console.error('Error completing task', error);
    }
  };

  return (
    <div>
      <ul>
        {tasks && tasks.length > 0 && tasks.map((task) => (
          <li key={task.id}>
            <span style={{ textDecoration: completedTasks.includes(task.id) ? 'line-through' : 'none' }}>
              {task.description} - {task.type} - {task.priority}
              {task.type === 'Data' && ` - ${task.completionDate}`}
              {task.type === 'Prazo' && ` - ${task.daysToCompletion} dias`}
            </span>
            <button onClick={() => handleDelete(task.id)}>Delete</button>
            <button onClick={() => handleComplete(task.id)}>Concluir</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TodoList;
