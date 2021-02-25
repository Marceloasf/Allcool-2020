import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { PartnerContainer, PartnerList, PartnerMap } from '../../screens';
import { rootStackOptions, screenStackOptions } from '../../styles';
import { RouteProp } from '@react-navigation/native';
import { MenuActionButton } from '../../components';
import { DrawerNavigationProp } from '@react-navigation/drawer';

type PartnerRootStackParamList = {
  PartnerStack: { userId: string };
  Partners: { userId: string };
  PartnerContainer: { userId: string };
  PartnerMap: { userId: string };
};

const RootStack = createStackNavigator<PartnerRootStackParamList>();

type PartnerStackRouteProp = RouteProp<
  PartnerRootStackParamList,
  'PartnerStack'
>;

type PartnerDrawerNavigationProp = DrawerNavigationProp<
  PartnerRootStackParamList,
  'PartnerStack'
>;

type Props = {
  navigation: PartnerDrawerNavigationProp;
  route: PartnerStackRouteProp;
};

const PartnerStack: React.FC<Props> = ({
  navigation,
  route: {
    params: { userId },
  },
}) => {
  return (
    <>
      <RootStack.Navigator
        initialRouteName="Partners"
        screenOptions={rootStackOptions('Parceiros')}
      >
        <RootStack.Screen
          name="Partners"
          component={PartnerList}
          initialParams={{ userId }}
          options={{
            ...screenStackOptions('Parceiros'),
            headerLeft: () => (
              <MenuActionButton onPress={() => navigation.openDrawer()} />
            ),
          }}
        />
        <RootStack.Screen
          name="PartnerContainer"
          options={screenStackOptions('Parceiro')}
          component={PartnerContainer}
          initialParams={{ userId }}
        />
        <RootStack.Screen
          name="PartnerMap"
          options={screenStackOptions('Mapa dos Parceiros')}
          component={PartnerMap}
          initialParams={{ userId }}
        />
      </RootStack.Navigator>
    </>
  );
};

export { PartnerStack };
