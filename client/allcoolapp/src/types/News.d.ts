import { FileDTO } from './dto';
import { NewsTypeEnum } from './enum';

export type News = {
  id?: string;
  address?: string;
  file?: FileDTO;
  description?: string;
  rating?: number;
  creationDate?: string;
  eventDate?: string;
  type?: NewsTypeEnum;
};
