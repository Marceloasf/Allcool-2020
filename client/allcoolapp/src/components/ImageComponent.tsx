import React, { useState } from 'react';
import { Image, ImageResizeMode, StyleProp, ImageStyle } from 'react-native';
import { ActivityIndicator } from 'react-native-paper';
import { listImageStyle } from '../styles';

type Props = {
  url: string;
  imageStyle: StyleProp<ImageStyle>;
  resizeMode?: ImageResizeMode;
  loadingStyle?: StyleProp<ImageStyle>;
};

const ImageComponent: React.FC<Props> = ({
  url,
  resizeMode = 'contain',
  imageStyle,
  loadingStyle,
}) => {
  const [imageLoaded, setImageLoaded] = useState(false);

  return (
    <>
      <Image
        onLoadEnd={() => setImageLoaded(true)}
        style={imageLoaded && imageStyle}
        source={{
          uri: url,
        }}
        resizeMode={resizeMode}
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

export { ImageComponent };
