import requestExecutor from './AxiosService';
import { ProductFileDTO } from '../types/dto';
import { AxiosPromise } from 'axios';

const resource = '/api/products-files';

export const findAllByProductId = (
  id: string
): AxiosPromise<ProductFileDTO[]> => {
  return requestExecutor.get(`${resource}/${id}`);
};
