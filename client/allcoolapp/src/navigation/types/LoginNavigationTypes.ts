import { StackNavigationProp } from '@react-navigation/stack';

type LoginStackParamList = {
  Drawer: { userId: string };
};

export type LoginNavigationProp = StackNavigationProp<
  LoginStackParamList,
  'Drawer'
>;
