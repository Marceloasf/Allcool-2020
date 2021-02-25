import { StackNavigationProp } from '@react-navigation/stack';
import { RouteProp } from '@react-navigation/native';

type PublicationListStackParamList = {
  Publications: { userId: string };
  PublicationNewsView: { userId: string; newsId: string };
  PublicationReviewView: { reviewId: string };
};

export type PublicationListNavigationProp = StackNavigationProp<
  PublicationListStackParamList,
  'Publications'
>;

export type PublicationListRouteProp = RouteProp<
  PublicationListStackParamList,
  'Publications'
>;

type PublicationViewStackParamList = {
  PublicationView: { userId: string; newsId: string };
};

export type PublicationViewNavigationProp = StackNavigationProp<
  PublicationViewStackParamList,
  'PublicationView'
>;

export type PublicationViewRouteProp = RouteProp<
  PublicationViewStackParamList,
  'PublicationView'
>;
