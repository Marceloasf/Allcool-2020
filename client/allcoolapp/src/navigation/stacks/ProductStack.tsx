import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import {
  ProductView,
  ProductList,
  ProductReview,
  AchievementList,
  AchievementView,
} from '../../screens';
import { rootStackOptions, screenStackOptions } from '../../styles';
import { RouteProp } from '@react-navigation/native';
import { DrawerNavigationProp } from '@react-navigation/drawer';
import { MenuActionButton } from '../../components';

type ProductRootStackParamList = {
  ProductStack: { userId: string };
  Products: { userId: string };
  ProductView: { userId: string };
  ProductReview: { userId: string };
  AchievementList: { userId: string };
  AchievementView: { userId: string };
};

const RootStack = createStackNavigator<ProductRootStackParamList>();

type ProductStackRouteProp = RouteProp<
  ProductRootStackParamList,
  'ProductStack'
>;

type ProductDrawerNavigationProp = DrawerNavigationProp<
  ProductRootStackParamList,
  'ProductStack'
>;

type Props = {
  navigation: ProductDrawerNavigationProp;
  route: ProductStackRouteProp;
};

const ProductStack: React.FC<Props> = ({
  navigation,
  route: {
    params: { userId },
  },
}) => {
  return (
    <>
      <RootStack.Navigator
        initialRouteName="Products"
        screenOptions={rootStackOptions('Produtos')}
      >
        <RootStack.Screen
          name="Products"
          component={ProductList}
          initialParams={{ userId }}
          options={{
            ...screenStackOptions('Produtos'),
            headerLeft: () => (
              <MenuActionButton onPress={() => navigation.openDrawer()} />
            ),
          }}
        />
        <RootStack.Screen
          name="ProductView"
          options={screenStackOptions('Produto')}
          component={ProductView}
          initialParams={{ userId }}
        />
        <RootStack.Screen
          name="ProductReview"
          options={screenStackOptions('Avaliação')}
          component={ProductReview}
          initialParams={{ userId }}
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

export { ProductStack };
