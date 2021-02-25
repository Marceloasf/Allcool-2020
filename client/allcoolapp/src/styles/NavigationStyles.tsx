import React from 'react';
import { StackNavigationOptions } from '@react-navigation/stack';
import { View } from 'react-native';

export const rootStackOptions = (
  headerTitle: string
): StackNavigationOptions => ({
  headerTitle,
  headerStyle: {
    backgroundColor: '#ffbf00',
  },
  headerTitleStyle: {
    textAlign: 'center',
    flexGrow: 1,
    alignSelf: 'center',
    fontFamily: 'Bebas Neue Pro Bold',
    fontSize: 28,
  },
  headerTitleContainerStyle: {
    alignSelf: 'center',
  },
  headerTintColor: '#fff',
});

export const screenStackOptions = (
  headerTitle: string
): StackNavigationOptions => ({
  headerTitle,
  headerRight: () => <View />,
});
