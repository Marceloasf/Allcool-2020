import React, { useEffect, useState } from 'react';
import {
  PublicationViewRouteProp,
  PublicationViewNavigationProp,
} from '../../../navigation';
import { Paragraph, Title } from 'react-native-paper';
import { NewsDTO } from '../../../types/dto';
import {
  ImageComponent,
  Loading,
  ReadOnlyStarRating,
  SnackbarNotification,
  SnackbarState,
} from '../../../components';
import { useLoading } from '../../../hooks';
import { View } from 'react-native';
import { NewsService } from '../../../service';
import { ScrollView } from 'react-native-gesture-handler';
import { mainStyles } from '../../../styles';

type Props = {
  route: PublicationViewRouteProp;
  navigation: PublicationViewNavigationProp;
};

const PublicationNewsView: React.FC<Props> = ({
  navigation,
  route: {
    params: { newsId },
  },
}) => {
  const [loading, setLoading] = useLoading();
  const [news, setNews] = useState<NewsDTO>({
    id: '',
  });
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });

  useEffect(() => {
    setLoading(
      NewsService.findById(newsId)
        .then(({ data }) => setNews(data))
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        )
    );
    //eslint-disable-next-line
  }, [newsId]);

  return (
    <>
      {loading ? (
        <Loading />
      ) : (
        <ScrollView style={mainStyles.container}>
          {news.pictureUrl && (
            <View>
              <View style={{ alignSelf: 'center', marginTop: '3%' }}>
                <ImageComponent
                  imageStyle={{ width: 400, height: 240 }}
                  resizeMode="contain"
                  url={news.pictureUrl!}
                />
              </View>
            </View>
          )}
          <Title style={mainStyles.title}>{news.title}</Title>
          <Paragraph style={[{ fontSize: 15, color: '#A2A2A2' }]}>
            {`${
              news.address?.id &&
              `Endereço: ${news.address.publicPlace}, ${news.address.locality}, ${news.address.federatedUnit}\n`
            }Data: ${news.eventDate}`}
          </Paragraph>
          <ReadOnlyStarRating rating={news.rating!} />
          <Paragraph style={[{ fontSize: 18 }]}>{news.description}</Paragraph>
        </ScrollView>
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

export { PublicationNewsView };
