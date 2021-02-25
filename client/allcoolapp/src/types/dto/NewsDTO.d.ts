import { Address } from '../Address';
import { NewsTypeEnum } from '../enum';

export type NewsDTO = {
  id?: string;
  address?: Address;
  title?: string;
  description?: string;
  rating?: number;
  pictureUrl?: string;
  eventDate?: string;
  type?: NewsTypeEnum;
};
