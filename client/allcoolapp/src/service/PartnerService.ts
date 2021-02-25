import requestExecutor from './AxiosService';
import { AxiosPromise } from 'axios';
import { PartnerDTO, PartnerViewDTO } from '../types/dto';

const resource = '/api/partners';

export const findAll = (): AxiosPromise<PartnerDTO[]> => {
  return requestExecutor.get(resource);
};

export const findById = (id: string): AxiosPromise<PartnerViewDTO> => {
  return requestExecutor.get(`${resource}/${id}`);
};
