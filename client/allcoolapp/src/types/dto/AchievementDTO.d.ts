import { AchievementTypeEnum } from '../enum';

export type AchievementDTO = {
  id: string;
  brand: string;
  badgeUrl: string;
  product: string;
  achievementName: string;
  description: string;
  type: AchievementTypeEnum;
};
