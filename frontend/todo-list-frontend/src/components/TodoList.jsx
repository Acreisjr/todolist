import React from 'react';
import { deleteTask, completeTask } from '../api';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CloseIcon from '@mui/icons-material/Close';

const TodoList = ({ tasks, onEdit, onTaskDeleted }) => {

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
      onTaskDeleted();
    } catch (error) {
      console.error('Error completing task', error);
    }
  };

  return (
    <ul>
      {tasks && tasks.length > 0 && tasks.map((task) => (
        <li key={task.id}>
          <div className="task-content">
            <span  className={`task-description ${task.status === 'Concluída' ? 'CompletedTask' : ''}`}>{task.description}</span>
            <div className="task-icons">
              {task.status === 'Concluída' ? <CheckCircleIcon /> : <CloseIcon />}
            </div>
          </div>
          <div className="task-buttons">
            <button onClick={() => handleDelete(task.id)}>Delete</button>
            <button onClick={() => handleComplete(task.id)}>Concluir</button>
          </div>
        </li>
      ))}
    </ul>
  );
};

export default TodoList;