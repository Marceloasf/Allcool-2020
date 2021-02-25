import React, { useState, useEffect } from 'react';
import { RefreshControl, View, Dimensions } from 'react-native';
import {
  ProfileViewRouteProp,
  ProfileViewNavigationProp,
} from '../../navigation';
import { Card } from 'react-native-paper';
import { FlatList } from 'react-native-gesture-handler';
import {
  EmptyListPlaceholder,
  SnackbarNotification,
  Loading,
  SnackbarState,
} from '../../components';
import { ReviewPublicationCardChildren } from '../publication/review';
import { useLoading } from '../../hooks';
import { PublicationDTO, UserClientDTO } from '../../types/dto';
import { UserClientService, PublicationService } from '../../service';
import { ProfileViewHeader } from './ProfileViewHeader';

type Props = {
  route: ProfileViewRouteProp;
  navigation: ProfileViewNavigationProp;
};

const dimensions = Dimensions.get('window');
const screenWidth = dimensions.width;

const ProfileView: React.FC<Props> = ({
  route: {
    params: { userId },
  },
  navigation,
}) => {
  const [loading, setLoading] = useLoading();
  const [loggedUser, setLoggedUser] = useState<UserClientDTO>({
    id: userId,
    bio: '',
    name: '',
    userPicture: undefined,
  });
  const [publications, setPublications] = useState<PublicationDTO[]>([]);
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });

  const refreshPublications = () =>
    setLoading(
      PublicationService.findAllReviewPublicationsByUserId(loggedUser.id!)
        .then(({ data }) => setPublications(data))
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        )
    );

  useEffect(() => {
    setLoading(
      UserClientService.findById(userId)
        .then(({ data }) => setLoggedUser(data))
        .then(() => refreshPublications())
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        )
    );
    //eslint-disable-next-line
  }, [userId]);

  return (
    <>
      {!loading ? (
        <FlatList
          data={publications}
          style={{
            flex: 1,
            width: screenWidth,
          }}
          ListHeaderComponent={
            <ProfileViewHeader
              loggedUser={loggedUser}
              navigation={navigation}
            />
          }
          ListEmptyComponent={
            <EmptyListPlaceholder
              message="Nenhuma publicação encontrada"
              marginTop="30"
            />
          }
          refreshControl={
            <RefreshControl
              refreshing={loading}
              onRefresh={refreshPublications}
            />
          }
          keyExtractor={(item) => item.id!}
          renderItem={({ item, index }) => (
            <View
              style={{
                width: '95%',
                alignSelf: 'center',
                marginTop: '3%',
                marginBottom: '2%',
              }}
            >
              <Card accessibilityStates style={{ backgroundColor: '#f7f7f7' }}>
                <ReviewPublicationCardChildren
                  creationDate={item.creationDate!}
                  review={item.review!}
                  itemIndex={index}
                />
              </Card>
            </View>
          )}
        />
      ) : (
        <Loading />
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

export { ProfileView };
