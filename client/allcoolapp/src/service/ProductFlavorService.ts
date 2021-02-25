import requestExecutor from './AxiosService';
import { AxiosPromise } from 'axios';
import { ProductFlavorDTO } from '../types/dto';

const resource = '/api/products-flavors';

export const findAllByProductId = (
  id: string
): AxiosPromise<ProductFlavorDTO[]> => {
  return requestExecutor.get(`${resource}/${id}`);
};
