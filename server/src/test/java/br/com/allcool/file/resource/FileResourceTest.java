package br.com.allcool.file.resource;

import br.com.allcool.file.domain.File;
import br.com.allcool.file.service.FileService;
import br.com.allcool.test.ResourceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ResourceTest(FileResource.class)
public class FileResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService service;

    @Test
    public void saveReviewPicture() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "fileData",
                "teste.jpeg",
                "text/plain",
                "{\"key1\": \"value1\"}".getBytes());

        when(this.service.createImage(mockMultipartFile, "files", UUID.randomUUID())).thenReturn(new File());

        this.mockMvc.perform(multipart("/api/files")
                .file("image", mockMultipartFile.getBytes())
                .characterEncoding("UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        verify(this.service).createImage(any(), any(), any());
        verifyNoMoreInteractions(this.service);
    }
}
