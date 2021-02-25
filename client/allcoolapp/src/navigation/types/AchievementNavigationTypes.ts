import { StackNavigationProp } from '@react-navigation/stack';
import { RouteProp } from '@react-navigation/native';

type AchievementListStackParamList = {
  AchievementList: { userId: string; productId?: string };
  AchievementView: { achievementId: string };
};

export type AchievementListNavigationProp = StackNavigationProp<
  AchievementListStackParamList,
  'AchievementList'
>;

export type AchievementListRouteProp = RouteProp<
  AchievementListStackParamList,
  'AchievementList'
>;

type AchievementViewStackParamList = {
  AchievementView: { achievementId: string; userId: string };
};

export type AchievementViewNavigationProp = StackNavigationProp<
  AchievementViewStackParamList,
  'AchievementView'
>;

export type AchievementViewRouteProp = RouteProp<
  AchievementViewStackParamList,
  'AchievementView'
>;
