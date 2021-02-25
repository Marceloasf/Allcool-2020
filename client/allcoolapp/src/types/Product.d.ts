import { Brand } from './Brand';
import { ProductType } from './ProductType';
import { ProductFlavor } from './ProductFlavor';
import { ProductContainer } from './ProductContainer';

export type Product = {
  id?: string;
  type?: ProductType;
  code?: number;
  name?: string;
  description?: string;
  harmonization?: string;
  active?: boolean;
  containers?: ProductContainer[];
  flavors?: ProductFlavor[];
  brand?: Brand;
  rating?: number;
  ibu?: number;
  minimumTemperature?: number;
  maximumTemperature?: number;
  alcoholContent?: number;
};
