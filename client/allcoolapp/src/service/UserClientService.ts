import requestExecutor from './AxiosService';
import { AxiosPromise } from 'axios';
import { UserClientDTO } from '../types/dto';

const resource = '/api/users';

export const findById = (userId: string): AxiosPromise<UserClientDTO> => {
  return requestExecutor.get(`${resource}/${userId}`);
};
