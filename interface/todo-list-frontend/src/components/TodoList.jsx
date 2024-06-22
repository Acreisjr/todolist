import React, { useEffect, useState } from 'react';
import { getTasks, deleteTask } from '../api';

const TodoList = ({ onEdit }) => {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await getTasks();
      setTasks(response.data);
    } catch (error) {
      console.error('Error fetching tasks', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteTask(id);
      fetchTasks(); 
    } catch (error) {
      console.error('Error deleting task', error);
    }
  };

  return (
    <div>
      <h2>Task List</h2>
      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            {task.title} - {task.type} - {task.priority}
            <button onClick={() => handleDelete(task.id)}>Delete</button>
            <button onClick={() => onEdit(task)}>Edit</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TodoList;
