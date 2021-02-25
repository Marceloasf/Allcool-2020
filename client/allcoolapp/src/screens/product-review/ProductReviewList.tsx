import React, { useEffect, useState } from 'react';
import {
  Loading,
  SnackbarState,
  ReadOnlyStarRating,
  CardCoverImageComponent,
} from '../../components';
import { useLoading } from '../../hooks';
import { ReviewService } from '../../service';
import { ReviewDTO } from '../../types/dto';
import { View } from 'react-native';
import { Card, Paragraph, Avatar, Subheading } from 'react-native-paper';
import moment from 'moment';
import { mainStyles } from '../../styles';

type Props = {
  productId: string;
  setSnackbarState: (snackbarState: SnackbarState) => void;
};

const ProductReviewList: React.FC<Props> = ({
  productId,
  setSnackbarState,
}) => {
  const [loading, setLoading] = useLoading();
  const [reviews, setReviews] = useState<ReviewDTO[]>([]);

  useEffect(() => {
    setLoading(
      ReviewService.findAllByProductId(productId)
        .then(({ data }) => setReviews(data))
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        )
    );
    //eslint-disable-next-line
  }, [productId]);

  return (
    <>
      {loading ? (
        <Loading />
      ) : (
        <View>
          {reviews && reviews.length > 0 ? (
            reviews?.map((reviewDTO, index) => (
              <View
                key={index}
                style={{
                  width: '95%',
                  alignSelf: 'center',
                  marginTop: '3%',
                  marginBottom: '2%',
                }}
              >
                <Card
                  accessibilityStates
                  style={{
                    backgroundColor: '#f7f7f7',
                  }}
                >
                  {reviewDTO.pictureUrl && (
                    <CardCoverImageComponent
                      url={reviewDTO.pictureUrl}
                      cardHeight={125}
                    />
                  )}
                  <Card.Title
                    accessibilityStates
                    titleStyle={{ fontSize: 22 }}
                    subtitle={`Data: ${moment(reviewDTO.creationDate!)
                      .locale('pt-br')
                      .format('DD/MM/YYYY')}`}
                    subtitleStyle={{ fontSize: 16 }}
                    title={reviewDTO.userName}
                    left={() => (
                      <Avatar.Image
                        accessibilityStates
                        size={40}
                        source={{ uri: reviewDTO.avatarUrl }}
                      />
                    )}
                    right={() => (
                      <ReadOnlyStarRating
                        rating={reviewDTO.rating!}
                        onFlexEnd
                      />
                    )}
                  />
                  <Card.Content>
                    <Paragraph style={{ fontSize: 16 }}>
                      {reviewDTO.description}
                    </Paragraph>
                  </Card.Content>
                </Card>
              </View>
            ))
          ) : (
            <View
              style={{
                alignItems: 'center',
                marginTop: '5%',
                marginBottom: '5%',
              }}
            >
              <Subheading style={mainStyles.subHeading}>
                Nenhum registro encontrado
              </Subheading>
            </View>
          )}
        </View>
      )}
    </>
  );
};

export { ProductReviewList };
