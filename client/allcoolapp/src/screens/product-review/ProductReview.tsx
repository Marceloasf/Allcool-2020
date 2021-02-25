import React, { useState, useEffect } from 'react';
import { View, SafeAreaView } from 'react-native';
import { ReviewFormDTO, ProductFlavorDTO } from '../../types/dto';
import { Rating } from 'react-native-ratings';
import { mainStyles } from '../../styles';
import {
  TextInput,
  Divider,
  Button,
  Chip,
  Subheading,
  Title,
} from 'react-native-paper';
import { ScrollView } from 'react-native-gesture-handler';
import { ProductFlavorService, ReviewService } from '../../service';
import { ProductReviewCard } from './ProductReviewCard';
import {
  ProductReviewNavigationProp,
  ProductReviewRouteProp,
} from '../../navigation';
import {
  Loading,
  pictureInitialStatus,
  RequiredFieldMarker,
  SnackbarNotification,
  SnackbarState,
} from '../../components';
import { TakePictureResponse } from 'react-native-camera';
import { useLoading } from '../../hooks';

type Props = {
  navigation: ProductReviewNavigationProp;
  route: ProductReviewRouteProp;
};

const initialValue = (
  productId: string,
  userClientId: string
): ReviewFormDTO => ({
  id: '',
  userClientId,
  productId,
  description: '',
  rating: 0,
  flavors: [],
});

const ProductReview: React.FC<Props> = ({
  navigation,
  route: {
    params: { product, userId },
  },
}) => {
  const [review, setReview] = useState<ReviewFormDTO>(
    initialValue(product.id!, userId)
  );
  const [productFlavors, setProductFlavors] = useState<ProductFlavorDTO[]>([]);
  const [showPic, setShowPic] = useState(false);
  const [picture, setPicture] = useState<TakePictureResponse>(
    pictureInitialStatus
  );
  const [isValid, setIsValid] = useState(false);
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });
  const [loading, setLoading] = useLoading();

  const isRated: boolean = !!(review.rating && review.rating > 0);

  useEffect(() => {
    if (product.id) {
      ProductFlavorService.findAllByProductId(product.id)
        .then(({ data }) => setProductFlavors(data))
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        );
    }
    //eslint-disable-next-line
  }, [product.id]);

  useEffect(() => {
    setIsValid(
      !!(
        review.rating &&
        review.description?.length! > 0 &&
        review.flavors?.length! > 0
      )
    );
  }, [review.rating, review.description, review.flavors]);

  const submitReview = () =>
    setLoading(
      ReviewService.saveReview(review)
        .then(({ data }) => {
          const formData = new FormData();
          formData.append('image', {
            uri: picture.uri || '',
            type: 'image/jpeg',
            name: `review_${data.id}_picture.jpg`,
          });

          return { formData, data };
        })
        .then(({ formData, data }) => {
          if (picture.uri) {
            return ReviewService.saveReviewImage(formData, data.id!);
          }
        })
        .then(() => navigation.goBack())
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        )
    );

  const handleChange = (name: string, value) =>
    setReview((prevReview) => ({
      ...prevReview,
      [name]: value,
    }));

  const updateReviewFlavors = (updatedFlavor: ProductFlavorDTO) => {
    if (updatedFlavor.selected) {
      return setReview((prevState) => {
        const newReviewFlavorArray = [...prevState.flavors!, updatedFlavor];

        return {
          ...prevState,
          flavors: newReviewFlavorArray,
        };
      });
    }

    return setReview((prevState) => {
      const flavorIndex = review.flavors!.findIndex(
        (r) => r.id === updatedFlavor.id
      );

      const newReviewFlavorArray = [
        ...prevState.flavors!.slice(0, flavorIndex),
        ...prevState.flavors!.slice(flavorIndex + 1),
      ];

      return {
        ...prevState,
        flavors: newReviewFlavorArray,
      };
    });
  };

  const selectFlavor = (flavor: ProductFlavorDTO, index: number) => {
    const updatedFlavor = { ...flavor, selected: !flavor.selected };

    const newProductFlavorArray = [
      ...productFlavors.slice(0, index),
      updatedFlavor,
      ...productFlavors.slice(index + 1),
    ];

    setProductFlavors(newProductFlavorArray);
    return updateReviewFlavors(updatedFlavor);
  };

  return (
    <>
      {loading ? (
        <Loading />
      ) : (
        <ScrollView>
          <SafeAreaView style={mainStyles.container}>
            <View style={{ alignItems: 'flex-start', marginTop: '3%' }}>
              <Title style={mainStyles.title}>Avalie o produto</Title>
            </View>
            <View style={{ alignItems: 'flex-start' }}>
              <Subheading style={mainStyles.subHeading}>
                {product.name}
              </Subheading>
            </View>

            <ProductReviewCard
              showPic={showPic}
              setShowPic={setShowPic}
              picture={picture}
              setPicture={setPicture}
            />

            <View>
              <View>
                <View
                  style={{
                    marginTop: '5%',
                  }}
                >
                  <Divider accessibilityStates />
                </View>
                <View>
                  <View style={{ alignItems: 'flex-start', marginTop: '3%' }}>
                    <Title style={mainStyles.title}>
                      Classificação{<RequiredFieldMarker />}
                    </Title>
                    <Subheading style={mainStyles.subHeading}>
                      Escolha de 1 a 5 estrelas para classificar.
                    </Subheading>
                  </View>
                  <View
                    style={{
                      flex: 1,
                      marginTop: '2%',
                      alignItems: 'flex-start',
                    }}
                  >
                    <View>
                      <Rating
                        type="star"
                        startingValue={review.rating}
                        imageSize={30}
                        onFinishRating={(rating) =>
                          handleChange('rating', rating || 0)
                        }
                      />
                    </View>
                  </View>
                  <View
                    style={{
                      marginTop: '5%',
                    }}
                  >
                    <Divider accessibilityStates />
                  </View>
                </View>
              </View>

              <View>
                <View style={{ alignItems: 'flex-start', marginTop: '3%' }}>
                  <Title style={mainStyles.title}>
                    O que você achou?{<RequiredFieldMarker />}
                  </Title>
                </View>
                <TextInput
                  accessibilityStates
                  style={[mainStyles.input, { width: '100%', marginTop: '1%' }]}
                  mode="outlined"
                  placeholder="Comenta aí!"
                  theme={{
                    colors: { primary: '#ffbf00' },
                  }}
                  multiline={true}
                  maxLength={200}
                  onChangeText={(value) => handleChange('description', value)}
                />
              </View>

              {isRated && (
                <>
                  <View
                    style={{
                      marginTop: '5%',
                    }}
                  >
                    <Divider accessibilityStates />
                  </View>
                  <View style={{ alignItems: 'flex-start', marginTop: '3%' }}>
                    <Title style={mainStyles.title}>
                      Notou algum desses sabores?{<RequiredFieldMarker />}
                    </Title>
                  </View>
                  <View
                    style={{
                      flex: 1,
                      marginTop: '5%',
                      flexDirection: 'row',
                      flexWrap: 'wrap',
                    }}
                  >
                    {productFlavors?.map((flavor, index) => (
                      <View
                        key={index}
                        style={{
                          margin: '1%',
                        }}
                      >
                        <Chip
                          accessibilityStates
                          key={flavor.id}
                          mode="outlined"
                          textStyle={{ color: 'black', fontSize: 14 }}
                          selectedColor="black"
                          style={{
                            backgroundColor: flavor.selected
                              ? '#ffbf00'
                              : '#e0e0e0',
                          }}
                          selected={flavor.selected}
                          onPress={() => selectFlavor(flavor, index)}
                        >
                          {flavor.description}
                        </Chip>
                      </View>
                    ))}
                  </View>
                </>
              )}
            </View>
          </SafeAreaView>
          <View
            style={[
              mainStyles.container,
              { marginBottom: '5%', marginTop: '5%' },
            ]}
          >
            <View>
              <Button
                accessibilityStates
                mode="contained"
                theme={{
                  colors: { primary: '#ffbf00' },
                }}
                onPress={() => submitReview()}
                disabled={!isValid}
                labelStyle={mainStyles.buttonText}
              >
                Avaliar
              </Button>
            </View>
          </View>
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

export { ProductReview };
