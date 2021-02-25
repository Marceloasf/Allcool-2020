import { FileDTO } from './FileDTO';
import { WorkingPeriodDTO } from './WorkingPeriodDTO';

export type PartnerViewDTO = {
  id?: string;
  name?: string;
  description?: string;
  phoneNumber?: string;
  rating?: number;
  fileDTO?: FileDTO;
  workingPeriodDTOList?: WorkingPeriodDTO[];
  address?: string;
  locality?: string;
};
