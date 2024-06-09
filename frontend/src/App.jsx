import React from 'react';
import { TodoWrapper } from './components/TodoWrapper';
import axios from 'https://cdn.skypack.dev/axios';

axios.defaults.baseURL = 'todolist-cuw0.onrender.com/api';

export function App() {
  return (
    <TodoWrapper />
  );
}

console.log('Hello console');
