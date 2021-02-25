package br.com.allcool.user.resource;

import br.com.allcool.dto.FileDTO;
import br.com.allcool.dto.UserClientDTO;
import br.com.allcool.test.ResourceTest;
import br.com.allcool.user.service.UserClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ResourceTest(UserClientResource.class)
public class UserClientResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserClientService service;

    @Test
    public void findById() throws Exception {

        UUID userId = UUID.randomUUID();

        UserClientDTO userClientDTO = new UserClientDTO();
        userClientDTO.setId(userId);
        userClientDTO.setUserPicture(new FileDTO(UUID.randomUUID(), "www.teste.com"));
        userClientDTO.setName("Zé");
        userClientDTO.setBio("Um Mestre Cervejeiro");

        when(this.service.findById(userId)).thenReturn(userClientDTO);

        this.mockMvc.perform(get("/api/users/{userId}", userId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(userClientDTO.getId().toString())))
                .andExpect(jsonPath("$.userPicture.id",
                        equalTo(userClientDTO.getUserPicture().getId().toString())))
                .andExpect(jsonPath("$.userPicture.url", equalTo(userClientDTO.getUserPicture().getUrl())))
                .andExpect(jsonPath("$.name", equalTo(userClientDTO.getName())))
                .andExpect(jsonPath("$.bio", equalTo(userClientDTO.getBio())));

        verify(this.service).findById(userId);
        verifyNoMoreInteractions(this.service);
    }
}