import React, { useState, useEffect } from 'react';
import { View, FlatList, TouchableOpacity, Dimensions } from 'react-native';
import {
  Divider,
  Title,
  Subheading,
  Searchbar,
  IconButton,
} from 'react-native-paper';
import { PartnerService } from '../../service';
import { rowStyle, mainStyles } from '../../styles';
import { PartnerDTO } from '../../types/dto';
import {
  PartnerListNavigationProp,
  PartnersListRouteProp,
} from '../../navigation';
import {
  EmptyListPlaceholder,
  SnackbarState,
  SnackbarNotification,
} from '../../components';
import { useLoading } from '../../hooks';

type Props = {
  navigation: PartnerListNavigationProp;
  route: PartnersListRouteProp;
};

const dimensions = Dimensions.get('window');
const screenWidth = dimensions.width;

const PartnerList: React.FC<Props> = ({
  navigation,
  route: {
    params: { userId },
  },
}) => {
  const [partners, setPartners] = useState<PartnerDTO[]>([]);
  const [filteredPartners, setFilteredPartners] = useState<PartnerDTO[]>([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useLoading();
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });

  useEffect(() => {
    setLoading(
      PartnerService.findAll()
        .then(({ data }) => {
          setPartners(data);
          setFilteredPartners(data);
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

  const filter = () => {
    const filteredArray = partners.filter((p) =>
      p.name.trim().toLowerCase().includes(search.trim().toLowerCase())
    );

    setFilteredPartners(filteredArray);
  };

  const view = (partner: PartnerDTO) =>
    navigation.navigate(`PartnerContainer`, {
      partnerId: partner.id,
    });

  const showPartnerLocalization = (partner: PartnerDTO) =>
    navigation.navigate(`PartnerMap`, {
      partnerId: partner.id,
    });

  const handleChange = (text: string) => {
    if (text) {
      return setSearch(text);
    }

    setFilteredPartners(partners);
    setSearch('');
  };

  return (
    <>
      <Searchbar
        accessibilityStates
        placeholder="Pesquisar"
        onChangeText={(text) => handleChange(text)}
        onBlur={filter}
        value={search}
      />
      {filteredPartners && filteredPartners.length > 0 ? (
        <FlatList
          data={filteredPartners}
          style={{
            flex: 1,
            width: screenWidth,
          }}
          keyExtractor={(item) => item.id}
          renderItem={({ item }) => (
            <>
              <TouchableOpacity onPress={() => view(item)}>
                <View style={rowStyle}>
                  <View style={{ marginLeft: '5%', marginTop: '6.5%' }}>
                    <View style={{ alignItems: 'flex-start' }}>
                      <Title style={mainStyles.title}>{item.name}</Title>
                    </View>
                    <View>
                      <Subheading style={mainStyles.subHeading}>
                        {`${
                          item.address.publicPlace.length > 40
                            ? item.address.publicPlace
                                .slice(0, 40)
                                .concat('...')
                            : item.address.publicPlace
                        }`}
                      </Subheading>
                    </View>
                    <View>
                      <Subheading style={mainStyles.subHeading}>
                        {`${
                          item.address.locality.length > 40
                            ? item.address.locality.slice(0, 40).concat('...')
                            : item.address.locality
                        }`}
                        - {`${item.phoneNumber}`}
                      </Subheading>
                    </View>
                  </View>
                  <View
                    style={{
                      paddingLeft: '5%',
                      marginTop: '10%',
                      flex: 1,
                      flexDirection: 'row-reverse',
                    }}
                  >
                    <IconButton
                      accessibilityStates
                      icon="map-search-outline"
                      color={'#ffbf00'}
                      size={40}
                      onPress={() => showPartnerLocalization(item)}
                    />
                  </View>
                </View>
                <View style={{ marginTop: '6.5%', backgroundColor: '#ffbf00' }}>
                  <Divider accessibilityStates style={{ height: 0.5 }} />
                </View>
              </TouchableOpacity>
            </>
          )}
        />
      ) : (
        <EmptyListPlaceholder loading={loading} />
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

export { PartnerList };
