import React, { useState } from 'react';

export const TodoForm = ({ addTodo }) => {
  const [description, setDescription] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (description.trim()) {
      addTodo({
        description,
        completed: false
      });
      setDescription('');
    }
  };

  return (
    <form className="TodoForm" onSubmit={handleSubmit}>
      <input
        type="text"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        className="todo-input"
        placeholder="Descrição da Tarefa"
      />
      <button type="submit" className="todo-btn">Adicionar Tarefa</button>
    </form>
  );
};
