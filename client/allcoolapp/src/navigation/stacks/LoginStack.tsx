import React from 'react';
import { LoginContainer } from '../../screens';
import { createStackNavigator } from '@react-navigation/stack';

type LoginRootStackParamList = {
  Login: undefined;
};

const LoginRootStack = createStackNavigator<LoginRootStackParamList>();

const LoginStack: React.FC = () => {
  return (
    <>
      <LoginRootStack.Navigator
        initialRouteName="Login"
        screenOptions={{
          headerShown: false,
        }}
      >
        <LoginRootStack.Screen name="Login" component={LoginContainer} />
      </LoginRootStack.Navigator>
    </>
  );
};

export { LoginStack };
