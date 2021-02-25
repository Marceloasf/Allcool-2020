import React from 'react';
import { UserClientDTO } from '../../types/dto';
import {
  Avatar,
  Title,
  Divider,
  Paragraph,
  IconButton,
} from 'react-native-paper';
import { mainStyles, rowStyle } from '../../styles';
import { View } from 'react-native';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import { ProfileViewNavigationProp } from 'src/navigation';
import moment from 'moment';

type Props = {
  loggedUser: UserClientDTO;
  navigation: ProfileViewNavigationProp;
};

const ProfileViewHeader: React.FC<Props> = ({ loggedUser, navigation }) => {
  return (
    <>
      <View style={mainStyles.container}>
        <View style={[rowStyle, { marginTop: '1%' }]}>
          <Avatar.Image
            accessibilityStates
            size={50}
            source={
              loggedUser.userPicture?.url
                ? { uri: loggedUser.userPicture.url }
                : require('../../img/AllcoolV1.1.png')
            }
            style={{
              backgroundColor: 'white',
              marginTop: '2%',
            }}
          />
          <View
            style={{
              flex: 1,
              flexDirection: 'row-reverse',
            }}
          >
            <IconButton
              accessibilityStates
              icon="account-cog"
              color="#ffbf00"
              animated
              size={32}
            />
          </View>
        </View>
        <View>
          <Title style={[mainStyles.title]}>{loggedUser.name}</Title>
        </View>
        <View>
          <Paragraph
            style={[
              {
                fontSize: 18,
                marginBottom: '5%',
              },
            ]}
          >
            {loggedUser.bio}
          </Paragraph>
        </View>
        {loggedUser.location && (
          <View style={rowStyle}>
            <MaterialCommunityIcons name="map-marker" color="grey" size={20} />
            <Paragraph
              style={[
                {
                  color: 'grey',
                  marginLeft: '1%',
                  fontSize: 18,
                },
              ]}
            >
              {loggedUser.location}
            </Paragraph>
          </View>
        )}
        <View style={{ marginBottom: '3%' }}>
          <View style={rowStyle}>
            <MaterialCommunityIcons name="calendar" color="grey" size={20} />
            <Paragraph
              style={[
                {
                  color: 'grey',
                  marginLeft: '1%',
                  fontSize: 18,
                },
              ]}
            >
              Nascido em{' '}
              {`${moment(loggedUser.birthDate!)
                .locale('pt-br')
                .format('DD/MM/YYYY')}`}
            </Paragraph>
          </View>
        </View>
        <View style={rowStyle}>
          <Paragraph
            style={[
              {
                fontSize: 18,
              },
            ]}
          >
            {/* TODO - AJUSTAR ASSIM QUE AS CONEXÕES FOREM IMPLEMENTADAS */}
            {`15 `}
          </Paragraph>
          <Paragraph
            style={[
              {
                color: 'grey',
                fontSize: 18,
              },
            ]}
          >
            Conexões
          </Paragraph>
          <Paragraph
            style={[
              {
                marginLeft: '5%',
                fontSize: 18,
              },
            ]}
          >
            {`25 `}
          </Paragraph>
          <Paragraph
            onPress={() => navigation.navigate('AchievementList')}
            style={[
              {
                color: 'grey',
                fontSize: 18,
              },
            ]}
          >
            Conquistas
          </Paragraph>
        </View>
      </View>
      <View style={{ marginTop: '3%', backgroundColor: '#ffbf00' }}>
        <Divider accessibilityStates style={{ height: 1 }} />
      </View>
    </>
  );
};

export { ProfileViewHeader };
