import React, { useEffect, useState } from 'react';
import { View } from 'react-native';
import {
  PartnerContainerNavigationProp,
  PartnerContainerRouteProp,
} from '../../navigation';
import { ScrollView } from 'react-native-gesture-handler';
import { boldTextStyles, textStyles, mainStyles } from '../../styles';
import { Headline, Chip, Text, FAB } from 'react-native-paper';
import { PartnerService } from '../../service';
import { PartnerViewDTO } from '../../types/dto';
import {
  SnackbarNotification,
  SnackbarState,
  Loading,
  ImageComponent,
} from '../../components';
import { useLoading } from '../../hooks';

type Props = {
  navigation: PartnerContainerNavigationProp;
  route: PartnerContainerRouteProp;
};

const PartnerContainer: React.FC<Props> = ({
  navigation,
  route: {
    params: { partnerId, userId },
  },
}) => {
  const [loading, setLoading] = useLoading();
  const [partner, setPartner] = useState<PartnerViewDTO>({});
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });

  useEffect(() => {
    setLoading(
      PartnerService.findById(partnerId)
        .then(({ data }) => setPartner(data))
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        )
    );
    //eslint-disable-next-line
  }, [partnerId, userId]);

  const renderChip = (key, value) => {
    return (
      <View style={{ marginLeft: '3%' }}>
        <Chip
          accessibilityStates
          key={key}
          mode="outlined"
          textStyle={{ color: 'black', fontSize: 14 }}
          selectedColor="black"
          style={{
            backgroundColor: '#ffbf00',
          }}
        >
          {value}
        </Chip>
      </View>
    );
  };

  return (
    <>
      {loading ? (
        <Loading />
      ) : (
        <ScrollView style={{ flex: 1 }}>
          <View>
            <View style={{ alignSelf: 'center' }}>
              <ImageComponent
                imageStyle={{ width: 400, height: 240 }}
                resizeMode="stretch"
                url={partner.fileDTO?.url!}
              />
            </View>
          </View>

          <View style={mainStyles.container}>
            <View style={{ alignItems: 'flex-start' }}>
              <View style={{ marginTop: '3%' }}>
                <Headline style={boldTextStyles}>{partner.name}</Headline>
              </View>
              <View style={{ marginTop: '2%' }}>
                <Text accessibilityStates style={textStyles}>
                  {partner.description}
                </Text>
              </View>

              <View style={{ marginTop: '2%' }}>
                <Headline style={boldTextStyles}>Endereço</Headline>
              </View>

              <View>
                <Text accessibilityStates style={textStyles}>
                  {partner.address}
                </Text>
                <Text accessibilityStates style={textStyles}>
                  {partner.locality}
                </Text>
                <Text accessibilityStates style={textStyles}>
                  {`Telefone: ${partner.phoneNumber}`}
                </Text>
              </View>
            </View>

            <View style={{ marginTop: '2%' }}>
              <Headline style={boldTextStyles}>
                Horário de Funcionamento
              </Headline>
            </View>
            <View
              style={{
                flex: 1,
                marginTop: '2%',
                flexWrap: 'nowrap',
              }}
            >
              {partner.workingPeriodDTOList?.map((wp, index) => (
                <View
                  key={index}
                  style={{
                    margin: '1%',
                    flexDirection: 'row',
                  }}
                >
                  <View
                    style={{
                      marginTop: '3%',
                      alignItems: 'stretch',
                      width: 85,
                    }}
                  >
                    <Text
                      accessibilityStates
                      style={textStyles}
                    >{`${wp.day}:`}</Text>
                  </View>
                  {renderChip(wp.id, wp.openingTime)}
                  {renderChip(wp.id, wp.closingTime)}
                </View>
              ))}
            </View>
          </View>
        </ScrollView>
      )}
      <FAB
        accessibilityStates
        style={mainStyles.fab}
        icon="map-marker-radius"
        onPress={() => navigation.navigate(`PartnerMap`, { partnerId })}
      />

      <SnackbarNotification
        snackbarState={snackbarState}
        dismissSnackbar={() =>
          setSnackbarState({
            message: '',
            visible: false,
          })
        }
      />
    </>
  );
};

export { PartnerContainer };
