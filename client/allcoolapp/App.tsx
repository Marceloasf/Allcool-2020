import 'react-native-gesture-handler';
import React from 'react';

import { NavigationContainer } from '@react-navigation/native';
import { AppRootStack, LoginStack, DrawerStack } from './src/navigation';
import {
  configureFonts,
  DefaultTheme,
  Provider as PaperProvider,
  Theme,
} from 'react-native-paper';
import { Fonts } from 'react-native-paper/lib/typescript/src/types';

const fontConfig: Fonts = {
  regular: {
    fontFamily: 'Bebas Neue Pro Regular',
    fontWeight: 'normal',
  },
  medium: {
    fontFamily: 'Bebas Neue Pro Bold',
    fontWeight: 'normal',
  },
  light: {
    fontFamily: 'Bebas Neue Pro Light',
    fontWeight: 'normal',
  },
  thin: {
    fontFamily: 'Bebas Neue Pro Thin',
    fontWeight: 'normal',
  },
};

const paperTheme: Theme = {
  ...DefaultTheme,
  fonts: configureFonts({ default: fontConfig }),
};

const navigationTheme = {
  dark: false,
  colors: {
    primary: 'rgb(255, 45, 85)',
    background: 'rgb(255, 255, 255)',
    card: 'rgb(255, 255, 255)',
    text: 'rgb(28, 28, 30)',
    border: 'rgb(199, 199, 204)',
  },
};

const App = () => {
  return (
    <>
      <PaperProvider theme={paperTheme}>
        <NavigationContainer theme={navigationTheme}>
          <AppRootStack.Navigator
            initialRouteName="Login"
            screenOptions={{ headerShown: false }}
          >
            <AppRootStack.Screen name="Login" component={LoginStack} />
            <AppRootStack.Screen name="Drawer" component={DrawerStack} />
          </AppRootStack.Navigator>
        </NavigationContainer>
      </PaperProvider>
    </>
  );
};

export default App;
