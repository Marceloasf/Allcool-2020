import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import {
  PublicationList,
  PublicationNewsView,
  PublicationReviewView,
} from '../../screens';
import { rootStackOptions, screenStackOptions } from '../../styles';
import { RouteProp } from '@react-navigation/native';
import { MenuActionButton } from '../../components';
import { DrawerNavigationProp } from '@react-navigation/drawer';

type PublicationRootStackParamList = {
  PublicationStack: { userId: string };
  Publications: { userId: string };
  PublicationNewsView: { userId: string };
  PublicationReviewView: undefined;
};

const RootStack = createStackNavigator<PublicationRootStackParamList>();

type PublicationStackRouteProp = RouteProp<
  PublicationRootStackParamList,
  'PublicationStack'
>;

type PublicationDrawerNavigationProp = DrawerNavigationProp<
  PublicationRootStackParamList,
  'PublicationStack'
>;

type Props = {
  navigation: PublicationDrawerNavigationProp;
  route: PublicationStackRouteProp;
};

const PublicationStack: React.FC<Props> = ({
  navigation,
  route: {
    params: { userId },
  },
}) => {
  return (
    <>
      <RootStack.Navigator
        initialRouteName="Publications"
        screenOptions={rootStackOptions('Publicações')}
      >
        <RootStack.Screen
          name="Publications"
          component={PublicationList}
          initialParams={{ userId }}
          options={{
            ...screenStackOptions('Publicações'),
            headerLeft: () => (
              <MenuActionButton onPress={() => navigation.openDrawer()} />
            ),
          }}
        />
        <RootStack.Screen
          name="PublicationNewsView"
          options={screenStackOptions('Publicação')}
          component={PublicationNewsView}
          initialParams={{ userId }}
        />
        <RootStack.Screen
          name="PublicationReviewView"
          options={screenStackOptions('Avaliação')}
          component={PublicationReviewView}
        />
      </RootStack.Navigator>
    </>
  );
};

export { PublicationStack };
