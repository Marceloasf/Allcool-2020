import requestExecutor from './AxiosService';
import { PublicationDTO } from '../types/dto';
import { AxiosPromise } from 'axios';

const resource = '/api/publications';

export const findAll = (): AxiosPromise<PublicationDTO[]> => {
  return requestExecutor.get(resource);
};

export const findAllReviewPublicationsByUserId = (
  userId: string
): AxiosPromise<PublicationDTO[]> => {
  return requestExecutor.get(`${resource}/${userId}`);
};
