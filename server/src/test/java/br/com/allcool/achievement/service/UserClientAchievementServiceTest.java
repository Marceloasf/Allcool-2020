package br.com.allcool.achievement.service;

import br.com.allcool.user.repository.UserClientAchievementRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserClientAchievementServiceTest {

    @InjectMocks
    private UserClientAchievementService service;

    @Mock
    private UserClientAchievementRepository userClientAchievementRepository;

    @Test
    public void countByUserId() {

        UUID userId = UUID.randomUUID();

        when(this.userClientAchievementRepository.countByUserId(userId)).thenReturn(1L);

        this.service.countByUserId(userId);

        verify(this.userClientAchievementRepository).countByUserId(userId);
        verifyNoMoreInteractions(this.userClientAchievementRepository);
    }

}
