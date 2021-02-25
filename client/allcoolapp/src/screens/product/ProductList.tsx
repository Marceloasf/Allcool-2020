import React, { useState, useEffect } from 'react';
import { View, FlatList, TouchableOpacity, Dimensions } from 'react-native';
import { ProductDTO } from '../../types/dto';
import { ProductService } from '../../service';
import { Divider, Title, Subheading, Searchbar } from 'react-native-paper';
import { listImageStyle, rowStyle, mainStyles } from '../../styles';
import {
  ProductsListRouteProp,
  ProductsListNavigationProp,
} from '../../navigation';
import { useLoading } from '../../hooks';
import {
  EmptyListPlaceholder,
  SnackbarState,
  SnackbarNotification,
  ImageComponent,
} from '../../components';

type Props = {
  route: ProductsListRouteProp;
  navigation: ProductsListNavigationProp;
};

const dimensions = Dimensions.get('window');
const screenWidth = dimensions.width;

const ProductList: React.FC<Props> = ({ navigation }) => {
  const [products, setProducts] = useState<ProductDTO[]>([]);
  const [filteredProducts, setFilteredProducts] = useState<ProductDTO[]>([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useLoading();
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });

  useEffect(() => {
    setLoading(
      ProductService.findAll()
        .then(({ data }) => {
          setProducts(data);
          setFilteredProducts(data);
        })
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        )
    );
    //eslint-disable-next-line
  }, []);

  const filter = () => {
    const filteredArray = products.filter((p) =>
      p.name!.trim().toLowerCase().includes(search.trim().toLowerCase())
    );

    setFilteredProducts(filteredArray);
  };

  const view = (product: ProductDTO) =>
    navigation.navigate(`ProductView`, {
      productId: product.id!,
    });

  const handleChange = (text: string) => {
    if (text) {
      return setSearch(text);
    }

    setFilteredProducts(products);
    setSearch('');
  };

  return (
    <>
      <Searchbar
        accessibilityStates
        placeholder="Pesquisar"
        onChangeText={(text) => handleChange(text)}
        onBlur={filter}
        value={search}
      />
      {filteredProducts && filteredProducts.length > 0 ? (
        <FlatList
          data={filteredProducts}
          style={{
            flex: 1,
            width: screenWidth,
          }}
          keyExtractor={(item) => item.id!}
          renderItem={({ item }) => (
            <>
              <TouchableOpacity onPress={() => view(item)}>
                <View style={rowStyle}>
                  <View style={{ alignItems: 'flex-start', marginTop: '2%' }}>
                    <ImageComponent
                      imageStyle={listImageStyle}
                      url={item.imageUrl!}
                    />
                  </View>
                  <View style={{ marginTop: '6.5%' }}>
                    <View style={{ alignItems: 'flex-start' }}>
                      <Title style={mainStyles.title}>{item.name}</Title>
                    </View>
                    <View style={{ alignItems: 'flex-start' }}>
                      <Subheading
                        style={mainStyles.subHeading}
                      >{`Categoria: ${item.type}`}</Subheading>
                    </View>
                    <View style={{ alignItems: 'flex-start' }}>
                      <Subheading
                        style={mainStyles.subHeading}
                      >{`Marca: ${item.brand}`}</Subheading>
                    </View>
                  </View>
                </View>
                <View style={{ marginTop: '2%', backgroundColor: '#ffbf00' }}>
                  <Divider accessibilityStates />
                </View>
              </TouchableOpacity>
            </>
          )}
        />
      ) : (
        <EmptyListPlaceholder loading={loading} />
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

export { ProductList };
