import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import axios from 'https://cdn.skypack.dev/axios';

interface TodoListProps {
  tasks: any[];
  fetchTasks: () => Promise<void>;
}

export const TodoList: React.FC<TodoListProps> = ({ tasks, fetchTasks }) => {

  const handleDelete = async (id: number) => {
    try {
      await axios.delete(`/task/${id}`);
      fetchTasks();
    } catch (error) {
      console.error('Erro ao deletar tarefa:', error);
    }
  };

  const handleComplete = async (id: number) => {
    try {
      await axios.patch(`/task/${id}`);
      fetchTasks();
    } catch (error) {
      console.error('Erro ao finalizar tarefa:', error);
    }
  };

  return (
    <>
      {Array.isArray(tasks) && tasks.map(task => (
        <div key={task.id} className={`Todo ${task.status === 'Concluída' ? 'completed' : 'incompleted'}`}>
          <p className={task.status === 'Concluída' ? 'completed' : 'incompleted'}>{task.description} - {task.status}</p>
          <div>
            <FontAwesomeIcon
              className='delete-icon'
              icon={faTrash}
              onClick={() => handleDelete(task.id)}
            />
            {task.status !== 'Concluída' && <button onClick={() => handleComplete(task.id)}>Finalizar</button>}
          </div>
        </div>
      ))}
    </>
  );
};
