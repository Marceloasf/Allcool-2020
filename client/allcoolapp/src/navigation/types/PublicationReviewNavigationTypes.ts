import { StackNavigationProp } from '@react-navigation/stack';
import { RouteProp } from '@react-navigation/native';

type PublicationReviewViewStackParamList = {
  PublicationReviewView: { reviewId: string };
};

export type PublicationReviewViewNavigationProp = StackNavigationProp<
  PublicationReviewViewStackParamList,
  'PublicationReviewView'
>;

export type PublicationReviewViewRouteProp = RouteProp<
  PublicationReviewViewStackParamList,
  'PublicationReviewView'
>;
