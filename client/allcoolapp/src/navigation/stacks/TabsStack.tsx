import React from 'react';
import { createMaterialBottomTabNavigator } from '@react-navigation/material-bottom-tabs';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import { RouteProp } from '@react-navigation/native';
import { PartnerStack, ProductStack, PublicationStack, ProfileStack } from './';

type TabsStackParamList = {
  Tabs: { userId: string };
  Products: { userId: string };
  Partners: { userId: string };
  Publications: { userId: string };
  Profile: { userId: string };
};

type TabsRouteProp = RouteProp<TabsStackParamList, 'Tabs'>;

type Props = {
  route: TabsRouteProp;
};

const Tab = createMaterialBottomTabNavigator<TabsStackParamList>();

const TabsStack: React.FC<Props> = ({
  route: {
    params: { userId },
  },
}) => {
  return (
    <Tab.Navigator
      initialRouteName="Products"
      activeColor="#ffbf00"
      shifting={false}
      keyboardHidesNavigationBar={true}
      barStyle={{ backgroundColor: '#ffffff' }}
    >
      <Tab.Screen
        name="Products"
        initialParams={{ userId }}
        component={ProductStack}
        options={{
          tabBarLabel: 'Produtos',
          tabBarIcon: ({ color }) => (
            <MaterialCommunityIcons name="beer" color={color} size={26} />
          ),
        }}
      />
      <Tab.Screen
        name="Partners"
        initialParams={{ userId }}
        component={PartnerStack}
        options={{
          tabBarLabel: 'Parceiros',
          tabBarIcon: ({ color }) => (
            <MaterialCommunityIcons
              name="account-multiple"
              color={color}
              size={26}
            />
          ),
        }}
      />
      <Tab.Screen
        name="Publications"
        initialParams={{ userId }}
        component={PublicationStack}
        options={{
          tabBarLabel: 'Publicações',
          tabBarIcon: ({ color }) => (
            <MaterialCommunityIcons name="view-list" color={color} size={26} />
          ),
        }}
      />
      <Tab.Screen
        name="Profile"
        initialParams={{ userId }}
        component={ProfileStack}
        options={{
          tabBarLabel: 'Perfil',
          tabBarIcon: ({ color }) => (
            <MaterialCommunityIcons
              name="account-circle"
              color={color}
              size={26}
            />
          ),
        }}
      />
    </Tab.Navigator>
  );
};

export { TabsStack };
