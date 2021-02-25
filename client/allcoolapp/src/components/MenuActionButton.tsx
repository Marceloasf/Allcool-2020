import React from 'react';
import { TouchableOpacity } from 'react-native-gesture-handler';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';

type Props = {
  onPress: (clickEvent) => void;
};

const MenuActionButton: React.FC<Props> = ({ onPress }) => {
  return (
    <TouchableOpacity style={{ marginLeft: 15 }} onPress={onPress}>
      <MaterialCommunityIcons name="menu" color="white" size={32} />
    </TouchableOpacity>
  );
};

export { MenuActionButton };
