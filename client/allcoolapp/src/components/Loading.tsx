import React from 'react';
import { View } from 'react-native';
import { listImageStyle } from '../styles';
import { ActivityIndicator } from 'react-native-paper';

const Loading: React.FC = () => {
  return (
    <View style={{ flex: 1 }}>
      <View style={{ alignItems: 'center', marginTop: '70%' }}>
        <ActivityIndicator
          accessibilityStates
          color="#ffbf00"
          size={50}
          style={listImageStyle}
        />
      </View>
    </View>
  );
};

export { Loading };
