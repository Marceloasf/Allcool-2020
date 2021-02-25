import { ContainerTypeEnum } from './enum/ContainerTypeEnum';

export type Container = {
  id: string;
  type: ContainerTypeEnum;
  capacity: number;
};
