import requestExecutor from './AxiosService';
import { AxiosPromise } from 'axios';
import { NewsDTO } from '../types/dto';

const resource = '/api/news';

export const findById = (id: string): AxiosPromise<NewsDTO> => {
  return requestExecutor.get(`${resource}/${id}`);
};
