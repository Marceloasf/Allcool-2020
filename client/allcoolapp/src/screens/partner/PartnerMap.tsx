import React, { useState, useEffect } from 'react';
import MapView, { Marker, PROVIDER_GOOGLE } from 'react-native-maps';
import Carousel from 'react-native-snap-carousel';
import { Dimensions, StyleSheet, View } from 'react-native';
import { PartnerService } from '../../service';
import { PartnerDTO } from '../../types/dto';
import {
  PartnerMapNavigationProp,
  PartnersMapRouteProp,
} from '../../navigation';
import {
  EmptyListPlaceholder,
  SnackbarState,
  SnackbarNotification,
  ImageComponent,
} from '../../components';
import { useLoading } from '../../hooks';
import Geolocation from '@react-native-community/geolocation';
import { mapStyle } from '../../styles/mapStyle';
import { listImageStyle, mainStyles, rowStyle } from '../../styles';
import { Button, Subheading, Title } from 'react-native-paper';

type Props = {
  navigation: PartnerMapNavigationProp;
  route: PartnersMapRouteProp;
};

type UserLocation = {
  latitude: number;
  longitude: number;
};

const ZOOM_LATITUDADE = 0.01;
const ZOOM_LONGITUDE = 0.01;

const { width } = Dimensions.get('window');

const CARD_HEIGHT = 220;
const CARD_WIDTH = width * 0.8;

const SLIDER_WIDTH = Dimensions.get('window').width;
const ITEM_WIDTH = Math.round(SLIDER_WIDTH * 0.7);

const mapRef = React.createRef<MapView>();

const PartnerMap: React.FC<Props> = ({
  navigation,
  route: {
    params: { partnerId },
  },
}) => {
  const [partners, setPartners] = useState<PartnerDTO[]>([]);
  const [partner, setPartner] = useState<PartnerDTO>();
  const [userLocation, setUserLocation] = useState<UserLocation>({
    latitude: 0,
    longitude: 0,
  });
  const [loading, setLoading] = useLoading();
  const [firstItem, setFirstItem] = useState<number | undefined>(undefined);
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });

  useEffect(() => {
    getUserPosition();

    setLoading(
      PartnerService.findAll()
        .then(({ data }) => {
          setPartners(data);

          const firstIndex = data.findIndex((p) => p.id === partnerId);

          if (firstIndex >= 0) {
            setPartner(data[firstIndex]);
            setFirstItem(firstIndex);
          }
        })
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        )
    );
    //eslint-disable-next-line
  }, []);

  const deniedAccessUserLocation = () => {
    setSnackbarState({
      message: 'É necessário informar sua localização para abrir o mapa',
      visible: true,
    });

    navigation.goBack();
  };

  const getUserPosition = () => {
    Geolocation.getCurrentPosition(
      (pos) => {
        setUserLocation({
          latitude: pos.coords.latitude,
          longitude: pos.coords.longitude,
        });
      },
      () => deniedAccessUserLocation()
    );
  };

  const showPartnerDetails = (partner: PartnerDTO) =>
    navigation.navigate(`PartnerContainer`, {
      partnerId: partner.id,
    });

  const centerMapOnMarker = (markerIndex: number) => {
    if (mapRef && mapRef.current && markerIndex >= 0) {
      const partnerSelected: PartnerDTO = { ...partners[markerIndex] };

      mapRef.current.animateToRegion({
        latitude: partnerSelected.address.latitude,
        longitude: partnerSelected.address.longitude,
        latitudeDelta: ZOOM_LATITUDADE,
        longitudeDelta: ZOOM_LONGITUDE,
      });
    }
  };

  const renderItem = ({ item }) => {
    return (
      <View style={mapChildrenStyle.card}>
        <View style={rowStyle}>
          {item.avatarUrl && (
            <View style={{ alignItems: 'flex-start', marginTop: '6%' }}>
              <ImageComponent
                imageStyle={listImageStyle}
                url={item.avatarUrl}
              />
            </View>
          )}
          <View style={{ marginTop: '6.5%', flex: 1 }}>
            <View style={{ alignItems: 'flex-start' }}>
              <Title numberOfLines={1} style={mainStyles.title}>
                {item.name}
              </Title>
            </View>
            <View style={{ alignItems: 'flex-start' }}>
              <Subheading style={mainStyles.subHeading} numberOfLines={1}>
                {`Cidade: ${item.address.locality} - ${item.address.federatedUnit}`}
              </Subheading>
            </View>
            <View style={{ alignItems: 'flex-start' }}>
              <Subheading style={mainStyles.subHeading} numberOfLines={1}>
                {`Bairro: ${item.address.district}`}
              </Subheading>
            </View>
            <View style={{ alignItems: 'flex-start' }}>
              <Subheading style={mainStyles.subHeading} numberOfLines={1}>
                {`Endereço: ${item.address.publicPlace}`}
              </Subheading>
            </View>
            <View style={{ alignItems: 'flex-start' }}>
              <Subheading style={mainStyles.subHeading} numberOfLines={1}>
                {`Telefone: ${item.phoneNumber}`}
              </Subheading>
            </View>
          </View>
        </View>

        <View style={{ marginTop: '5%' }}>
          <Button
            accessibilityStates
            color="#FFFFFF"
            onPress={() => showPartnerDetails(item)}
            mode="text"
            labelStyle={{ color: '#ffbf00' }}
          >
            Ver Mais Detalhes
          </Button>
        </View>
      </View>
    );
  };

  const showMap =
    userLocation && partner?.id && partners && partners.length > 0;

  return (
    <>
      {showMap ? (
        <View style={{ flex: 1 }}>
          <MapView
            ref={mapRef}
            region={{
              latitude: partner!.address.latitude,
              longitude: partner!.address.longitude,
              latitudeDelta: ZOOM_LATITUDADE,
              longitudeDelta: ZOOM_LONGITUDE,
            }}
            customMapStyle={mapStyle}
            provider={PROVIDER_GOOGLE}
            style={{ flex: 1 }}
            showsCompass={false}
            showsTraffic={false}
            showsIndoors={false}
            showsPointsOfInterest={true}
            showsBuildings={false}
          >
            {partners.map((value, index) => (
              <Marker
                key={index}
                title={value.name}
                coordinate={{
                  latitude: value.address.latitude,
                  longitude: value.address.longitude,
                }}
                image={require('../../img/allcoolIcon.png')}
              />
            ))}

            <Marker
              title={'Você está aqui!'}
              coordinate={{
                latitude: userLocation!.latitude,
                longitude: userLocation!.longitude,
              }}
            />
          </MapView>

          {partner?.id && firstItem! >= 0 && (
            <View style={mapChildrenStyle.scrollView}>
              <Carousel
                firstItem={firstItem}
                onSnapToItem={(index) => centerMapOnMarker(index)}
                data={partners || []}
                renderItem={renderItem}
                sliderWidth={SLIDER_WIDTH}
                itemWidth={ITEM_WIDTH}
                layout={'default'}
                inactiveSlideOpacity={0.5}
                activeAnimationType={'spring'}
              />
            </View>
          )}
        </View>
      ) : (
        <EmptyListPlaceholder
          message={
            'É necessário informar sua localização para abrir o Mapa dos Parceiros'
          }
          loading={loading}
        />
      )}
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

const mapChildrenStyle = StyleSheet.create({
  scrollView: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    paddingVertical: 10,
  },
  card: {
    padding: 10,
    elevation: 2,
    backgroundColor: '#FFF',
    borderTopLeftRadius: 5,
    borderTopRightRadius: 5,
    marginHorizontal: 10,
    shadowColor: '#000',
    shadowRadius: 5,
    shadowOpacity: 0.3,
    shadowOffset: { width: 2, height: -2 },
    height: CARD_HEIGHT,
    width: CARD_WIDTH,
    overflow: 'hidden',
  },
});

export { PartnerMap };
