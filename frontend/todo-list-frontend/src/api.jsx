import axios from 'axios';

const api = axios.create({
  baseURL: 'https://todolist-cuw0.onrender.com/api',
});

export const getTasks = () => api.get('/task');
export const createTask = (task) => api.post('/task', task);
export const completeTask = (id) => api.patch(`/task/${id}`);
export const deleteTask = (id) => api.delete(`/task/${id}`);

export default api;
