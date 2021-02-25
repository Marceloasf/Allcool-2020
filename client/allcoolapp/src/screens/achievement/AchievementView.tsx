import React, { useEffect, useState } from 'react';
import { View } from 'react-native';
import {
  ImageComponent,
  Loading,
  SnackbarNotification,
  SnackbarState,
} from '../../components';
import {
  AchievementViewNavigationProp,
  AchievementViewRouteProp,
} from '../../navigation';
import { boldTextStyles, mainStyles, textStyles } from '../../styles';
import { Text, Headline } from 'react-native-paper';
import { useLoading } from '../../hooks';
import { AchievementViewDTO } from '../../types/dto';
import { AchievementService } from '../../service';

type Props = {
  navigation: AchievementViewNavigationProp;
  route: AchievementViewRouteProp;
};

const AchievementView: React.FC<Props> = ({
  navigation,
  route: {
    params: { achievementId, userId },
  },
}) => {
  const [loading, setLoading] = useLoading();
  const [achievementView, setAchievement] = useState<AchievementViewDTO>({});
  const [snackbarState, setSnackbarState] = useState<SnackbarState>({
    message: '',
    visible: false,
  });

  useEffect(() => {
    setLoading(
      AchievementService.findById(achievementId)
        .then(({ data }) => setAchievement(data))
        .catch(({ response }) =>
          setSnackbarState({
            message: response.data?.message || response.data,
            visible: true,
          })
        )
    );
    //eslint-disable-next-line
  }, [achievementId, userId]);

  return (
    <>
      {loading ? (
        <Loading />
      ) : (
        <View style={[{ alignItems: 'center' }, mainStyles.container]}>
          <ImageComponent
            url={achievementView.achievemantFileUrl}
            imageStyle={{
              marginTop: '15%',
              height: 150,
              width: 150,
            }}
          />
          <View style={{ marginTop: '10%' }}>
            <Headline style={boldTextStyles}>{achievementView.title}</Headline>
          </View>

          <Text accessibilityStates style={textStyles}>
            {`Desbloqueada em 02 de Jul, 2020`}
          </Text>

          <View>
            <Text accessibilityStates style={[textStyles, { marginTop: '5%' }]}>
              {achievementView.description}
            </Text>
          </View>
        </View>
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

export { AchievementView };
