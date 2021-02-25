import React, { useState, useEffect } from 'react';
import { View } from 'react-native';
import { Product } from '../../types';
import { ScrollView } from 'react-native-gesture-handler';
import { Text, Headline, Button, Divider } from 'react-native-paper';
import {
  detailsStyle,
  boldTextStyles,
  textStyles,
  mainStyles,
  detailsTitleStyles,
  rowStyle,
} from '../../styles';
import {
  ProductService,
  ProductFileService,
  ReviewService,
} from '../../service';
import { ProductFileDTO } from '../../types/dto';
import {
  ProductViewNavigationProp,
  ProductViewRouteProp,
} from '../../navigation';
import {
  SnackbarNotification,
  SnackbarState,
  Loading,
  ImageComponent,
  ReadOnlyStarRating,
} from '../../components';
import { useLoading } from '../../hooks';
import { ProductReviewList } from '../product-review';

type Props = {
  navigation: ProductViewNavigationProp;
  route: ProductViewRouteProp;
};

const initialProductFile: ProductFileDTO = {
  id: '',
  url: '',
};

const ProductView: React.FC<Props> = ({
  navigation,
  route: {
    params: { productId, userId },
  },
}) => {
  const [loading, setLoading] = useLoading();
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });
  const [productReviewed, setProductReviewed] = useState(false);
  const [product, setProduct] = useState<Product>({});
  const [productFile, setProductFile] = useState<ProductFileDTO>(
    initialProductFile
  );
  const [showReview, setShowReview] = useState(false);

  const updateComponent = () => {
    if (productId && userId) {
      setLoading(
        ProductService.findById(productId)
          .then(({ data }) => setProduct(data))
          .then(() =>
            ReviewService.isProductReviewed(
              productId,
              userId
            ).then(({ data }) => setProductReviewed(data))
          )
          .then(() =>
            ProductFileService.findAllByProductId(productId).then(
              ({ data }) => data && setProductFile(data[0])
            )
          )
          .catch(({ response }) =>
            setSnackbarState({
              message: response.data?.message || response.data,
              visible: true,
            })
          )
      );
    }
  };

  useEffect(() => {
    navigation.addListener('focus', (payload) => {
      updateComponent();
    });
    //eslint-disable-next-line
  }, []);

  const onReview = () => {
    if (productReviewed) {
      return setSnackbarState({
        message: 'Opa, o produto já foi avaliado!',
        visible: true,
      });
    }

    if (userId) {
      navigation.navigate('ProductReview', {
        product: { id: productId, name: product.name },
      });
    }
  };

  return (
    <>
      {loading ? (
        <Loading />
      ) : (
        <>
          <ScrollView style={[mainStyles.container, { flex: 1 }]}>
            <View style={{ marginTop: '3%' }}>
              {!!productFile.id && (
                <View style={{ height: 40, alignSelf: 'center' }}>
                  <ImageComponent
                    imageStyle={{ width: 185, height: 205 }}
                    resizeMode="cover"
                    url={productFile.url}
                  />
                </View>
              )}
            </View>
            <View style={{ marginTop: '50%' }}>
              <View style={{ marginTop: '1%' }}>
                <Divider accessibilityStates style={{ height: 0.5 }} />
              </View>
              <View>
                <View style={[rowStyle, { marginTop: '1%' }]}>
                  <View style={{ right: '35%' }}>
                    <Button
                      onPress={onReview}
                      accessibilityStates
                      color="#FFFFFF"
                      mode="text"
                      labelStyle={{ color: '#ffbf00', fontSize: 20 }}
                    >
                      Avalie o produto
                    </Button>
                  </View>
                  <View
                    style={{
                      marginTop: '1%',
                      flex: 1,
                      flexDirection: 'row-reverse',
                    }}
                  >
                    <ReadOnlyStarRating rating={product.rating || 0} />
                  </View>
                </View>
                <View style={{ marginTop: '1%' }}>
                  <Divider accessibilityStates />
                </View>
                <View
                  style={{
                    marginTop: '1%',
                    alignItems: 'flex-start',
                    right: '4%',
                  }}
                >
                  <Button
                    accessibilityStates
                    color="#FFFFFF"
                    onPress={() =>
                      navigation.navigate('AchievementList', {
                        productId: product.id!,
                      })
                    }
                    mode="text"
                    labelStyle={{ color: '#ffbf00', fontSize: 20 }}
                  >
                    Conquistas
                  </Button>
                </View>
                <View style={{ marginTop: '1%' }}>
                  <Divider accessibilityStates />
                </View>
                <View style={{ marginTop: '1%' }}>
                  <Headline
                    style={boldTextStyles}
                  >{`Sobre ${product.name}`}</Headline>
                </View>
                <View>
                  <Text accessibilityStates style={textStyles}>
                    {product.description}
                  </Text>
                </View>

                <View style={{ marginTop: '2%' }}>
                  <Headline style={boldTextStyles}>Harmonização</Headline>
                </View>
                <View>
                  <Text accessibilityStates style={textStyles}>
                    {product.harmonization}
                  </Text>
                </View>
                <View style={{ marginTop: '2%' }}>
                  <Headline style={boldTextStyles}>Detalhes</Headline>
                </View>
              </View>
            </View>

            <View
              style={{
                marginTop: '1%',
                flexDirection: 'row',
                justifyContent: 'space-around',
              }}
            >
              <Text accessibilityStates style={detailsStyle}>
                <Text accessibilityStates style={detailsTitleStyles}>
                  Categoria:
                </Text>
                {` ${product.type && product.type.description}`}
              </Text>
              <Text accessibilityStates style={detailsStyle}>
                <Text accessibilityStates style={detailsTitleStyles}>
                  Teor Alcoólico:
                </Text>
                {` ${product.alcoholContent}%`}
              </Text>
            </View>

            <View
              style={{
                flexDirection: 'row',
                justifyContent: 'space-around',
                marginTop: '2%',
              }}
            >
              <Text accessibilityStates style={detailsStyle}>
                <Text accessibilityStates style={detailsTitleStyles}>
                  Temperatura Ideal:
                </Text>
                {` ${product.minimumTemperature}-${product.maximumTemperature}°C`}
              </Text>
              <Text accessibilityStates style={detailsStyle}>
                <Text accessibilityStates style={detailsTitleStyles}>
                  IBU:
                </Text>
                {` ${product.ibu}`}
              </Text>
            </View>

            <View style={{ marginTop: '4%' }}>
              {!showReview ? (
                <Button
                  accessibilityStates
                  color="#FFFFFF"
                  onPress={() => setShowReview(true)}
                  mode="text"
                  labelStyle={{ color: '#ffbf00' }}
                >
                  Avaliações relacionadas...
                </Button>
              ) : (
                <>
                  <Button
                    accessibilityStates
                    color="#FFFFFF"
                    onPress={() => setShowReview(false)}
                    mode="text"
                    labelStyle={{ color: '#ffbf00' }}
                  >
                    Ocultar avaliações...
                  </Button>
                </>
              )}
            </View>
            {showReview && (
              <ProductReviewList
                setSnackbarState={setSnackbarState}
                productId={product.id ?? ''}
              />
            )}
          </ScrollView>
        </>
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

export { ProductView };
