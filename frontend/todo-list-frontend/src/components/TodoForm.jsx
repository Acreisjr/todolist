// src/components/TodoForm.js
import React, { useState } from 'react';
import { createTask } from '../api';

const TodoForm = ({ onClose, onTaskCreated }) => {
  const [description, setDescription] = useState('');
  const [type, setType] = useState('Livre');
  const [priority, setPriority] = useState('Baixa');
  const [completionDate, setCompletionDate] = useState('');
  const [daysToCompletion, setDaysToCompletion] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    let task = { description, type, priority };

    if (type === 'Data') {
      task = { ...task, completionDate };
    } else if (type === 'Prazo') {
      task = { ...task, daysToCompletion: parseInt(daysToCompletion, 10) };
    }

    try {
      const response = await createTask(task);
      if (response.status === 201) {
        onTaskCreated(); 
        onClose(); 
      } else {
        console.error('Error creating task: ', response.data.message);
      }
    } catch (error) {
      console.error('Error creating task', error);
    }
  };

  return (
    <div className="modal">
      <form onSubmit={handleSubmit}>
        <h2>Criar Tarefa</h2>
        <div>
          <label>Descrição:</label>
          <input value={description} onChange={(e) => setDescription(e.target.value)} required />
        </div>
        <div>
          <label>Tipo:</label>
          <select value={type} onChange={(e) => setType(e.target.value)}>
            <option value="Livre">Livre</option>
            <option value="Data">Data</option>
            <option value="Prazo">Prazo</option>
          </select>
        </div>
        {type === 'Data' && (
          <div>
            <label>Data de conclusão:</label>
            <input type="date" value={completionDate} onChange={(e) => setCompletionDate(e.target.value)} />
          </div>
        )}
        {type === 'Prazo' && (
          <div>
            <label>Dias para conclusão:</label>
            <input type="number" value={daysToCompletion} onChange={(e) => setDaysToCompletion(e.target.value)} />
          </div>
        )}
        <div>
          <label>Prioridade:</label>
          <select value={priority} onChange={(e) => setPriority(e.target.value)}>
            <option value="Baixa">Baixa</option>
            <option value="Media">Média</option>
            <option value="Alta">Alta</option>
          </select>
        </div>
        <button type="submit">Criar</button>
        <button type="button" onClick={onClose}>Cancelar</button>
      </form>
    </div>
  );
};

export default TodoForm;
