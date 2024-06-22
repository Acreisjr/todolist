import React, { useState, useEffect } from 'react';
import TodoList from './components/TodoList';
import TodoForm from './components/TodoForm';
import Footer from './components/Footer';
import { getTasks } from './api';
import './App.css';

function App() {
  const [showModal, setShowModal] = useState(false);
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

  const handleTaskCreated = () => {
    fetchTasks();
    setShowModal(false);
  };

  const handleEditTask = (task) => {
    console.log('Edit task', task);
  };

  return (
    <div className="App">
      <main>
        <h1>Todo List</h1>
        <button onClick={() => setShowModal(true)}>Adicionar Tarefa</button>
        <TodoList tasks={tasks} onEdit={handleEditTask} onTaskDeleted={fetchTasks} />
        {showModal && (
          <TodoForm onClose={() => setShowModal(false)} onTaskCreated={handleTaskCreated} />
        )}
      </main>
      <Footer />
    </div>
  );
}

export default App;