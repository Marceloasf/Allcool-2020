import { FlavorTypeEnum } from '../enum';

export type ProductFlavorDTO = {
  id?: string;
  type?: FlavorTypeEnum;
  description?: string;
} & {
  selected?: boolean;
};
