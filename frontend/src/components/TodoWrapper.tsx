import React, { useState } from 'react';
import { TodoForm } from './TodoForm';
import { TodoList } from './TodoList';
import { v4 as uuidv4 } from 'uuid';

export const TodoWrapper = () => {
  const [todos, setTodos] = useState([]);

  const addTodo = (newTask) => {
    const newTodo = { ...newTask, id: uuidv4(), isEditing: false };
    setTodos([...todos, newTodo]);
  };

  const toggleComplete = (id) => {
    setTodos(todos.map(task => task.id === id ? { ...task, completed: !task.completed } : task));
  };

  const removeTodo = (id) => {
    setTodos(todos.filter(task => task.id !== id));
  };

  const editTodo = (id) => {
    setTodos(todos.map(task => task.id === id ? { ...task, isEditing: !task.isEditing } : task));
  };

  return (
    <div className="TodoWrapper">
      <h1>Lista de Tarefas</h1>
      <TodoForm addTodo={addTodo} />
      <TodoList tasks={todos} toggleComplete={toggleComplete} removeTodo={removeTodo} editTodo={editTodo} />
    </div>
  );
};
