import React from 'react';
import { Text } from 'react-native';

const RequiredFieldMarker: React.FC = () => (
  <Text style={{ color: 'red' }}> *</Text>
);

export { RequiredFieldMarker };
