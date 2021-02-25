import requestExecutor from './AxiosService';
import { AxiosPromise } from 'axios';
import { AchievementViewDTO, AchievementDTO } from '../types/dto';

const resource = '/api/achievements';

export const findById = (id: string): AxiosPromise<AchievementViewDTO> => {
  return requestExecutor.get(`${resource}/${id}/view`);
};

export const findAllAchievementByProductId = (
  productId: string
): AxiosPromise<AchievementDTO[]> => {
  return requestExecutor.get(`${resource}/${productId}`);
};

export const findAllByProductTypeId = (
  productTypeId: string
): AxiosPromise<AchievementDTO[]> => {
  return requestExecutor.get(`${resource}/product-types/${productTypeId}`);
};

export const findAllByBrandId = (
  brandId: string
): AxiosPromise<AchievementDTO[]> => {
  return requestExecutor.get(`${resource}/brands/${brandId}`);
};

export const countByProductId = (
  productId: string
): AxiosPromise<AchievementDTO> => {
  return requestExecutor.get(`${resource}/products/${productId}`);
};

export const findAllByUserId = (
  userId: string
): AxiosPromise<AchievementDTO[]> => {
  return requestExecutor.get(`${resource}/users/${userId}`);
};
