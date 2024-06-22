import React, { useState } from 'react';
import TodoList from './components/TodoList';
import TodoForm from './components/TodoForm';
import './App.css'; 

function App() {
  const [showModal, setShowModal] = useState(false);

  const handleTaskCreated = () => {
    setShowModal(false);
  };

  const handleEditTask = (task) => {
    console.log('Edit task', task);
  };

  return (
    <div className="App">
      <h1>Todo List</h1>
      <button onClick={() => setShowModal(true)}>+ Add Task</button>
      <TodoList onEdit={handleEditTask} />
      {showModal && (
        <TodoForm onClose={() => setShowModal(false)} onTaskCreated={handleTaskCreated} />
      )}
    </div>
  );
}

export default App;
