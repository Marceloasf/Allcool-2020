import React from 'react';
import { Snackbar } from 'react-native-paper';
import { Text } from 'react-native';

export type SnackbarState = {
  visible: boolean;
  message: string;
};

type Props = {
  snackbarState: SnackbarState;
  dismissSnackbar: () => void;
};

const SnackbarNotification: React.FC<Props> = ({
  snackbarState,
  dismissSnackbar,
}) => {
  return (
    <>
      <Snackbar
        accessibilityStates
        visible={snackbarState.visible}
        onDismiss={dismissSnackbar}
        style={{ backgroundColor: '#ffbf00' }}
        theme={{
          colors: {
            accent: 'black',
            surface: 'black',
          },
        }}
        duration={5000}
        action={{
          label: 'Ok',
          onPress: () => dismissSnackbar(),
        }}
      >
        <Text style={{ fontSize: 18 }}>{snackbarState.message}</Text>
      </Snackbar>
    </>
  );
};

export { SnackbarNotification };
