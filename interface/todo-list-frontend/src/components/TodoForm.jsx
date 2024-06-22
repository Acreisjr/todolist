import React, { useState } from 'react';
import { createTask } from '../api';

const TaskForm = ({ onClose, onTaskCreated }) => {
  const [title, setTitle] = useState('');
  const [type, setType] = useState('Livre');
  const [priority, setPriority] = useState('Baixa');
  const [completionDate, setCompletionDate] = useState('');
  const [daysToCompletion, setDaysToCompletion] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    let task = { title, type, priority };

    if (type === 'Data') {
      task = { ...task, completionDate };
    } else if (type === 'Prazo') {
      task = { ...task, daysToCompletion: parseInt(daysToCompletion, 10) };
    }

    try {
      await createTask(task);
      onTaskCreated();
      onClose(); 
    } catch (error) {
      console.error('Error creating task', error);
    }
  };

  return (
    <div className="modal">
      <form onSubmit={handleSubmit}>
        <h2>Create Task</h2>
        <div>
          <label>Title:</label>
          <input value={title} onChange={(e) => setTitle(e.target.value)} required />
        </div>
        <div>
          <label>Type:</label>
          <select value={type} onChange={(e) => setType(e.target.value)}>
            <option value="Livre">Livre</option>
            <option value="Data">Data</option>
            <option value="Prazo">Prazo</option>
          </select>
        </div>
        {type === 'Data' && (
          <div>
            <label>Completion Date:</label>
            <input type="date" value={completionDate} onChange={(e) => setCompletionDate(e.target.value)} />
          </div>
        )}
        {type === 'Prazo' && (
          <div>
            <label>Days to Completion:</label>
            <input type="number" value={daysToCompletion} onChange={(e) => setDaysToCompletion(e.target.value)} />
          </div>
        )}
        <div>
          <label>Priority:</label>
          <select value={priority} onChange={(e) => setPriority(e.target.value)}>
            <option value="Baixa">Baixa</option>
            <option value="Média">Média</option>
            <option value="Alta">Alta</option>
          </select>
        </div>
        <button type="submit">Create</button>
        <button type="button" onClick={onClose}>Cancel</button>
      </form>
    </div>
  );
};

export default TaskForm;
