import React from 'react';
import { Image, Linking, Modal, Text, View } from 'react-native';
import { Button } from 'react-native-paper';
import { mainStyles } from '../../styles';

type Props = {
  setShowModal: (value) => void;
};

const AgeConfirmationModal: React.FC<Props> = ({ setShowModal }) => (
  <Modal animationType="slide" transparent={false}>
    <View
      style={[
        mainStyles.container,
        { justifyContent: 'center', alignItems: 'center' },
      ]}
    >
      <View style={{ bottom: '10%' }}>
        <Image
          style={{ width: 150, height: 150 }}
          source={require('../../img/AllcoolV2.png')}
        />
      </View>
      <Text
        style={{
          fontSize: 28,
          fontFamily: 'Bebas Neue Pro Regular',
          bottom: '5%',
        }}
      >
        PARA VOCÊ ACESSAR ESSE CONTEÚDO,
      </Text>
      <Text
        style={{
          fontSize: 28,
          bottom: '5%',
          fontFamily: 'Bebas Neue Pro Regular',
        }}
      >
        PRECISAMOS CHECAR SUA IDADE.
      </Text>
      <View>
        <Text
          style={{
            fontSize: 22,
            fontFamily: 'Bebas Neue Pro Regular',
          }}
        >
          Você tem mais de 18 anos?
        </Text>
      </View>
      <View
        style={{
          flexDirection: 'row',
          marginTop: '4%',
        }}
      >
        <Button
          accessibilityStates
          style={{
            width: 160,
            backgroundColor: '#ffbf00',
            marginRight: '1%',
          }}
          onPress={() => setShowModal(false)}
          mode="contained"
          labelStyle={mainStyles.buttonText}
        >
          Sim
        </Button>
        <Button
          accessibilityStates
          style={{
            width: 160,
            backgroundColor: '#ffbf00',
            marginLeft: '1%',
          }}
          onPress={() =>
            Linking.openURL('https://www.ambev.com.br/termos-de-uso')
          }
          mode="contained"
          labelStyle={mainStyles.buttonText}
        >
          Não
        </Button>
      </View>
    </View>
  </Modal>
);

export { AgeConfirmationModal };
