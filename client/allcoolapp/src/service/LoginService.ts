import requestExecutor from './AxiosService';
import { LoginRequest, Person } from '../types';

const resource = '/auth';

export const login = (loginRequest: LoginRequest) => {
  return requestExecutor.post(`${resource}/login`, loginRequest);
};

export const register = (person: Person) => {
  return requestExecutor.post(`${resource}/register`, person);
};
