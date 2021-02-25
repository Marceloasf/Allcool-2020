import React, { useState } from 'react';
import {
  ImageResizeMode,
  StyleProp,
  ImageStyle,
  ViewStyle,
} from 'react-native';
import { ActivityIndicator, Card } from 'react-native-paper';
import { listImageStyle } from '../styles';

type Props = {
  url: string;
  resizeMode?: ImageResizeMode;
  loadingStyle?: StyleProp<ImageStyle>;
  cardStyle?: StyleProp<ViewStyle>;
  cardHeight?: number;
};

const CardCoverImageComponent: React.FC<Props> = ({
  url,
  resizeMode = 'contain',
  loadingStyle,
  cardStyle,
  cardHeight = 230,
}) => {
  const [imageLoaded, setImageLoaded] = useState(false);

  return (
    <>
      <Card.Cover
        accessibilityStates
        style={[cardStyle, { height: imageLoaded ? cardHeight : 0 }]}
        source={{ uri: url }}
        resizeMethod="auto"
        resizeMode={resizeMode}
        onLoadEnd={() => setImageLoaded(true)}
      />
      {!imageLoaded && (
        <ActivityIndicator
          accessibilityStates
          color="#ffbf00"
          size={50}
          style={loadingStyle ?? listImageStyle}
        />
      )}
    </>
  );
};

export { CardCoverImageComponent };
