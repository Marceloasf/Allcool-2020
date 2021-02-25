import { StyleSheet } from 'react-native';

export const mainStyles = StyleSheet.create({
  mainAppBackgroundColor: {
    backgroundColor: '#f2f2f2',
  },
  mainColor: {
    color: '#ffbf00',
  },
  container: {
    flex: 1,
    alignContent: 'center',
    paddingHorizontal: '3.3%',
    backgroundColor: '#fff',
  },
  input: {
    marginTop: '3%',
    width: 350,
    fontSize: 18,
    fontFamily: 'Bebas Neue Pro Regular',
    borderRadius: 3,
    backgroundColor: '#fff',
  },
  button: {
    width: 350,
    height: 42,
    backgroundColor: '#ffbf00',
    marginTop: '4%',
  },
  textButton: {
    fontSize: 16,
    color: '#fff',
    fontWeight: 'bold',
  },
  fab: {
    position: 'absolute',
    margin: 16,
    right: 0,
    bottom: 0,
    backgroundColor: '#ffbf00',
  },
  title: { fontSize: 24 },
  subHeading: {
    color: '#888888',
    fontSize: 16,
    marginTop: '-1%',
    fontFamily: 'Bebas Neue Pro Regular',
  },
  buttonText: {
    fontSize: 20,
    fontFamily: 'Bebas Neue Pro Regular',
  },
});
