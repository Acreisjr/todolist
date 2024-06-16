import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faPenToSquare } from '@fortawesome/free-solid-svg-icons';

export const TodoList = ({ tasks, toggleComplete, removeTodo, editTodo }) => {
  return (
    <div>
      {tasks.map((task) => (
        <div key={task.id} className="Todo">
          <p className={task.completed ? 'completed' : 'incompleted'}>
            {task.description}
          </p>
          <div>
            <button onClick={() => toggleComplete(task.id)}>Concluir</button>
            <FontAwesomeIcon className="edit-icon" icon={faPenToSquare} onClick={() => editTodo(task.id)} />
            <FontAwesomeIcon className="delete-icon" icon={faTrash} onClick={() => removeTodo(task.id)} />
          </div>
        </div>
      ))}
    </div>
  );
};
