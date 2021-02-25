import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { AchievementList, AchievementView, ProfileView } from '../../screens';
import { rootStackOptions, screenStackOptions } from '../../styles';
import { RouteProp } from '@react-navigation/native';
import { MenuActionButton } from '../../components';
import { DrawerNavigationProp } from '@react-navigation/drawer';

type ProfileRootStackParamList = {
  ProfileStack: { userId: string };
  ProfileView: { userId: string };
  AchievementList: { userId: string };
  AchievementView: { userId: string };
};

const RootStack = createStackNavigator<ProfileRootStackParamList>();

type ProfileStackRouteProp = RouteProp<
  ProfileRootStackParamList,
  'ProfileStack'
>;

type ProfileDrawerNavigationProp = DrawerNavigationProp<
  ProfileRootStackParamList,
  'ProfileStack'
>;

type Props = {
  navigation: ProfileDrawerNavigationProp;
  route: ProfileStackRouteProp;
};

const ProfileStack: React.FC<Props> = ({
  navigation,
  route: {
    params: { userId },
  },
}) => {
  return (
    <>
      <RootStack.Navigator
        initialRouteName="ProfileView"
        screenOptions={rootStackOptions('Perfil')}
      >
        <RootStack.Screen
          name="ProfileView"
          component={ProfileView}
          initialParams={{ userId }}
          options={{
            ...screenStackOptions('Perfil'),
            headerLeft: () => (
              <MenuActionButton onPress={() => navigation.openDrawer()} />
            ),
          }}
        />
        <RootStack.Screen
          name="AchievementList"
          options={screenStackOptions('Conquistas')}
          component={AchievementList}
          initialParams={{ userId }}
        />
        <RootStack.Screen
          name="AchievementView"
          options={screenStackOptions('Conquista')}
          component={AchievementView}
          initialParams={{ userId }}
        />
      </RootStack.Navigator>
    </>
  );
};

export { ProfileStack };
