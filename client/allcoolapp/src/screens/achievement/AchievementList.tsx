import React, { useState, useEffect } from 'react';
import { Dimensions, View } from 'react-native';
import { AchievementDTO } from '../../types/dto';
import { Divider, Searchbar, Subheading, Title } from 'react-native-paper';
import {
  AchievementListNavigationProp,
  AchievementListRouteProp,
} from '../../navigation';
import { useLoading } from '../../hooks';
import {
  EmptyListPlaceholder,
  ImageComponent,
  SnackbarNotification,
  SnackbarState,
} from '../../components';
import { AchievementService } from '../../service';
import { FlatList, TouchableOpacity } from 'react-native-gesture-handler';
import { listImageStyle, mainStyles, rowStyle } from '../../styles';

type Props = {
  navigation: AchievementListNavigationProp;
  route: AchievementListRouteProp;
};

const dimensions = Dimensions.get('window');
const screenWidth = dimensions.width;

const AchievementList: React.FC<Props> = ({
  navigation,
  route: {
    params: { productId, userId },
  },
}) => {
  const [achievements, setAchievements] = useState<AchievementDTO[]>([]);
  const [filteredAchievements, setFilteredAchievements] = useState<
    AchievementDTO[]
  >([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useLoading();
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });

  useEffect(() => {
    if (productId) {
      setLoading(
        AchievementService.findAllAchievementByProductId(productId)
          .then(({ data }) => {
            setAchievements(data);
            setFilteredAchievements(data);
          })
          .catch(({ response }) =>
            setSnackbarState({
              message: response.data?.message || response.data,
              visible: true,
            })
          )
      );
    } else if (userId) {
      setLoading(
        AchievementService.findAllByUserId(userId)
          .then(({ data }) => {
            setAchievements(data);
            setFilteredAchievements(data);
          })
          .catch(({ response }) =>
            setSnackbarState({
              message: response.data?.message || response.data,
              visible: true,
            })
          )
      );
    }
    //eslint-disable-next-line
  }, [productId, userId]);

  const filter = () => {
    const filteredArray = achievements.filter((p) =>
      p.achievementName
        .trim()
        .toLowerCase()
        .includes(search.trim().toLowerCase())
    );

    setFilteredAchievements(filteredArray);
  };

  const view = (id) =>
    navigation.navigate(`AchievementView`, {
      achievementId: id,
    });

  const handleChange = (text: string) => {
    if (text) {
      return setSearch(text);
    }

    setFilteredAchievements(achievements);
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
      {filteredAchievements && filteredAchievements.length > 0 ? (
        <FlatList
          data={filteredAchievements}
          style={{
            flex: 1,
            width: screenWidth,
          }}
          keyExtractor={(item) => item.id!}
          renderItem={({ item }) => (
            <>
              <TouchableOpacity onPress={() => view(item.id)}>
                <View style={rowStyle}>
                  <View style={{ alignItems: 'flex-start', marginTop: '2%' }}>
                    <ImageComponent
                      imageStyle={listImageStyle}
                      url={item.badgeUrl!}
                    />
                  </View>
                  <View style={{ marginTop: '6.5%' }}>
                    <View style={{ alignItems: 'flex-start' }}>
                      <Title style={mainStyles.title}>
                        {item.achievementName}
                      </Title>
                    </View>
                    {item.product && (
                      <View style={{ alignItems: 'flex-start' }}>
                        <Subheading
                          style={mainStyles.subHeading}
                        >{`Produto: ${item.product}`}</Subheading>
                      </View>
                    )}
                    <View style={{ alignItems: 'flex-start' }}>
                      <Subheading
                        style={mainStyles.subHeading}
                      >{`Marca: ${item.brand}`}</Subheading>
                    </View>
                    <View style={{ alignItems: 'flex-start' }}>
                      <Subheading
                        style={mainStyles.subHeading}
                      >{`Descrição: ${item.description}`}</Subheading>
                    </View>
                  </View>
                </View>
                <View style={{ marginTop: '2%', backgroundColor: '#ffbf00' }}>
                  <Divider accessibilityStates />
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

export { AchievementList };
