import React from 'react';
import { Card, Paragraph, Avatar, IconButton } from 'react-native-paper';
import { ReviewDTO } from '../../../types/dto';
import {
  CardCoverImageComponent,
  ReadOnlyStarRating,
} from '../../../components';
import moment from 'moment';

type Props = {
  creationDate: string;
  review: ReviewDTO;
  itemIndex: number;
  touched?: boolean;
  onLikePublication?: (index: number) => void;
};

const ReviewPublicationCardChildren: React.FC<Props> = ({
  creationDate,
  review,
  itemIndex,
  touched,
  onLikePublication,
}) => {
  return (
    <>
      {!!review.pictureUrl && (
        <CardCoverImageComponent url={review.pictureUrl} />
      )}
      <Card.Title
        accessibilityStates
        title={review.userName}
        subtitle={`${`${review.productName}\n`}Data: ${moment(creationDate)
          .locale('pt-br')
          .format('DD/MM/YYYY')}`}
        subtitleNumberOfLines={2}
        style={{ marginBottom: '2%' }}
        subtitleStyle={{ fontSize: 16 }}
        titleStyle={{ fontSize: 22 }}
        right={() => (
          <ReadOnlyStarRating rating={review.rating || 0} onFlexEnd />
        )}
        left={() => (
          <Avatar.Image
            accessibilityStates
            size={40}
            style={{ backgroundColor: '#ffbf00' }}
            source={{ uri: review.avatarUrl }}
          />
        )}
      />
      <Card.Content>
        <Paragraph
          style={{
            fontSize: 16,
            marginBottom: !onLikePublication ? '5%' : '0%',
          }}
        >
          {review.description}
        </Paragraph>
      </Card.Content>
      {onLikePublication && (
        <Card.Actions style={{ justifyContent: 'flex-end', margin: '-4%' }}>
          <IconButton
            accessibilityStates
            icon={touched ? 'beer' : 'beer-outline'}
            color="#ffbf00"
            animated
            size={36}
            onPress={() => onLikePublication(itemIndex)}
          />
        </Card.Actions>
      )}
    </>
  );
};

export { ReviewPublicationCardChildren };
