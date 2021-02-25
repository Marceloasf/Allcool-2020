import { createStackNavigator } from '@react-navigation/stack';

export type AppRootStackParamList = {
  Login: undefined;
  Drawer: undefined;
};

export const AppRootStack = createStackNavigator<AppRootStackParamList>();
