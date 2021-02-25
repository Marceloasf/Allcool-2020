import AsyncStorage from '@react-native-community/async-storage';

const StorageService = {
  storeKey: async (key, token) => {
    try {
      await AsyncStorage.setItem(key, token);
    } catch (e) {
      console.log(
        'Ocorreu um erro ao salvar a chave: ' + key + ' com o valor: ' + token
      );
    }
  },
  getKey: async (key) => {
    try {
      const value = await AsyncStorage.getItem(key);
      return value;
    } catch (e) {
      console.log('Ocorreu um erro ao buscar a chave: ' + key);
    }
  },
  removeKey: async (key) => {
    try {
      await AsyncStorage.removeItem(key);
    } catch (e) {
      console.log('Ocorreu um erro ao remover a chave: ' + key);
    }
  },
  storeUserClient: async (userId) => {
    try {
      await AsyncStorage.setItem('userId', userId);
    } catch (e) {
      console.log('Ocorreu um erro ao salvar a chave do usuário: ' + userId);
    }
  },
  clear: async () => {
    try {
      await AsyncStorage.clear();
    } catch (e) {
      console.log('Ocorreu um erro ao limpar todas as chaves');
    }
  },
};

export default StorageService;
