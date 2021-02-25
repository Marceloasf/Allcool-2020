import React from 'react';
import { View, Image } from 'react-native';
import { mainStyles } from '../styles';
import { Subheading } from 'react-native-paper';
import { Loading } from './Loading';

type Props = {
  loading?: boolean;
  marginTop?: string;
  message?: string;
};

const EmptyListPlaceholder: React.FC<Props> = ({
  loading = false,
  marginTop = '50',
  message = 'Nenhum registro encontrado',
}) => {
  return (
    <View style={{ flex: 1 }}>
      {!loading ? (
        <>
          <View
            style={{ alignItems: 'center', marginTop: marginTop.concat('%') }}
          >
            <Image
              style={{
                alignSelf: 'center',
                borderColor: '#ffbf00',
                width: 150,
                height: 140,
                marginRight: '2.5%',
                marginLeft: '2.5%',
              }}
              source={require('../img/AllcoolV2.png')}
              resizeMode="contain"
            />
          </View>
          <View style={{ alignItems: 'center', marginTop: '3%' }}>
            <Subheading style={mainStyles.subHeading}>{message}</Subheading>
          </View>
        </>
      ) : (
        <Loading />
      )}
    </View>
  );
};

export { EmptyListPlaceholder };
