import React, { useState, useEffect } from 'react';
import {
  PublicationListRouteProp,
  PublicationListNavigationProp,
} from '../../navigation';
import { View, Dimensions, RefreshControl } from 'react-native';
import { Card } from 'react-native-paper';
import { FlatList } from 'react-native-gesture-handler';
import {
  SnackbarState,
  EmptyListPlaceholder,
  SnackbarNotification,
  Loading,
} from '../../components';
import { PublicationDTO } from '../../types/dto';
import { useLoading } from '../../hooks';
import { PublicationService } from '../../service';
import { PublicationTypeEnum } from '../../types/enum';
import { ReviewPublicationCardChildren } from './review';
import { NewsPublicationCardChildren } from './news';

type Props = {
  route: PublicationListRouteProp;
  navigation: PublicationListNavigationProp;
};

const dimensions = Dimensions.get('window');
const screenWidth = dimensions.width;

const PublicationList: React.FC<Props> = ({
  navigation,
  route: {
    params: { userId },
  },
}) => {
  const [loading, setLoading] = useLoading();
  const [publications, setPublications] = useState<PublicationDTO[]>([]);
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });

  const onRefresh = () =>
    setLoading(
      PublicationService.findAll()
        .then(({ data }) => setPublications(data))
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        )
    );

  useEffect(() => {
    onRefresh();
    //eslint-disable-next-line
  }, []);

  const likePublication = (index: number) =>
    setPublications((prevState) => {
      const alteredPublication: PublicationDTO = {
        ...prevState[index],
        touched: !prevState[index].touched,
      };

      return [
        ...prevState.slice(0, index),
        alteredPublication,
        ...prevState.slice(index + 1),
      ];
    });

  const isReviewPublication = (item: PublicationDTO) =>
    item.type === PublicationTypeEnum.REVIEW;

  return (
    <>
      {!loading ? (
        <FlatList
          data={publications}
          style={{
            flex: 1,
            width: screenWidth,
          }}
          ListEmptyComponent={<EmptyListPlaceholder loading={loading} />}
          refreshControl={
            <RefreshControl refreshing={loading} onRefresh={onRefresh} />
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
              <Card
                accessibilityStates
                style={{ backgroundColor: '#f7f7f7' }}
                onPress={() => {
                  if (isReviewPublication(item)) {
                    return navigation.navigate('PublicationReviewView', {
                      reviewId: item.review?.id!,
                    });
                  }
                  navigation.navigate('PublicationNewsView', {
                    userId,
                    newsId: item.news?.id!,
                  });
                }}
              >
                {isReviewPublication(item) ? (
                  <ReviewPublicationCardChildren
                    creationDate={item.creationDate!}
                    review={item.review!}
                    itemIndex={index}
                    touched={item.touched!}
                    onLikePublication={likePublication}
                  />
                ) : (
                  <NewsPublicationCardChildren
                    news={item.news!}
                    itemIndex={index}
                    touched={item.touched!}
                    onLikePublication={likePublication}
                  />
                )}
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

export { PublicationList };
