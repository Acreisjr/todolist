import React, { useState } from 'react';
import axios from 'https://cdn.skypack.dev/axios';

interface TodoFormProps {
  fetchTasks: () => Promise<void>;
  closeModal: () => void;
}

export const TodoForm: React.FC<TodoFormProps> = ({ fetchTasks, closeModal }) => {
  const [task, setTask] = useState('');
  const [type, setType] = useState('LIVRE');
  const [priority, setPriority] = useState('LOW');
  const [completionDate, setCompletionDate] = useState('');
  const [daysToCompletion, setDaysToCompletion] = useState<number | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const taskData: any = { description: task, type, priority };

      if (type === 'DATA') {
        taskData.completionDate = completionDate;
      } else if (type === 'PRAZO') {
        taskData.daysToCompletion = daysToCompletion;
      }

      await axios.post('/task', taskData);
      fetchTasks();
      closeModal();
      setTask('');
      setCompletionDate('');
      setDaysToCompletion(null);
      setPriority('LOW');
      setType('LIVRE');
    } catch (error) {
      console.error('Erro ao criar tarefa:', error);
    }
  };

  return (
    <form className='TodoForm' onSubmit={handleSubmit}>
      <input 
        type="text"
        className="todo-input"
        placeholder="Qual é sua nova tarefa?"
        value={task}
        onChange={(e) => setTask(e.target.value)}
      />
      <select value={type} onChange={(e) => setType(e.target.value)}>
        <option value="LIVRE">Livre</option>
        <option value="DATA">Data</option>
        <option value="PRAZO">Prazo</option>
      </select>
      {type === 'DATA' && (
        <input
          type="date"
          value={completionDate}
          onChange={(e) => setCompletionDate(e.target.value)}
        />
      )}
      {type === 'PRAZO' && (
        <input
          type="number"
          placeholder="Dias para conclusão"
          value={daysToCompletion || ''}
          onChange={(e) => setDaysToCompletion(parseInt(e.target.value))}
        />
      )}
      <select value={priority} onChange={(e) => setPriority(e.target.value)}>
        <option value="HIGH">Alta</option>
        <option value="MEDIUM">Média</option>
        <option value="LOW">Baixa</option>
      </select>
      <button type="submit" className="todo-btn">Adicionar Tarefa</button>
    </form>
  );
};
