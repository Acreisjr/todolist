import React, { useState, useEffect } from 'react';
import axios from 'https://cdn.skypack.dev/axios';
import { TodoForm } from './TodoForm';
import { TodoList } from './TodoList';
import Modal from 'react-modal';

Modal.setAppElement('#root');

export const TodoWrapper: React.FC = () => {
  const [tasks, setTasks] = useState<any[]>([]);
  const [modalIsOpen, setModalIsOpen] = useState(false);

  const fetchTasks = async () => {
    try {
      const response = await axios.get('/task');
      setTasks(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      console.error('Erro ao buscar tarefas:', error);
    }
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  const openModal = () => setModalIsOpen(true);
  const closeModal = () => setModalIsOpen(false);

  return (
    <div className='App'>
      <h1>Lista de Tarefas</h1>
      <button onClick={openModal} className="todo-btn">+</button>
      <TodoList tasks={tasks} fetchTasks={fetchTasks} />
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={closeModal}
        contentLabel="Adicionar Tarefa"
        className="Modal"
        overlayClassName="Overlay"
      >
        <h2>Adicionar Nova Tarefa</h2>
        <TodoForm fetchTasks={fetchTasks} closeModal={closeModal} />
      </Modal>
    </div>
  );
};
