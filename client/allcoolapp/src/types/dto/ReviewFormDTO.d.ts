import { ProductFlavorDTO } from './ProductFlavorDTO';

export type ReviewFormDTO = {
  id?: string;
  userClientId?: string;
  productId?: string;
  description?: string;
  rating?: number;
  flavors?: ProductFlavorDTO[];
};
