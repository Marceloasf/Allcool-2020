package br.com.allcool.partner.resource;

import br.com.allcool.address.domain.Address;
import br.com.allcool.dto.FileDTO;
import br.com.allcool.dto.PartnerDTO;
import br.com.allcool.dto.PartnerViewDTO;
import br.com.allcool.dto.WorkingPeriodDTO;
import br.com.allcool.partner.service.PartnerService;
import br.com.allcool.test.ResourceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ResourceTest(PartnerResource.class)
public class PartnerResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartnerService service;

    @Test
    public void findById() throws Exception {

        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(UUID.randomUUID());
        fileDTO.setUrl("www.fileDTO.com");

        WorkingPeriodDTO workingPeriodDTO = new WorkingPeriodDTO();
        workingPeriodDTO.setId(UUID.randomUUID());
        workingPeriodDTO.setOpeningTime(LocalTime.of(16, 30));
        workingPeriodDTO.setDay("Sexta");
        workingPeriodDTO.setClosingTime(LocalTime.MIN);

        List<WorkingPeriodDTO> workingPeriodDTOList = new ArrayList<>();
        workingPeriodDTOList.add(workingPeriodDTO);

        PartnerViewDTO partner = new PartnerViewDTO();
        partner.setId(UUID.randomUUID());
        partner.setName("Bar do Azeitona");
        partner.setDescription("Famoso bar da cidade conhecido por muitos estudantes.");
        partner.setPhoneNumber("999999999");
        partner.setFileDTO(fileDTO);
        partner.setWorkingPeriodDTOList(workingPeriodDTOList);
        partner.setLocality("Maringa - PR");
        partner.setAddress("Rua teste, 111 - teste");

        when(this.service.findById(partner.getId())).thenReturn(partner);

        this.mockMvc.perform(get("/api/partners/{id}", partner.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(partner.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(partner.getName())))
                .andExpect(jsonPath("$.description", equalTo(partner.getDescription())))
                .andExpect(jsonPath("$.phoneNumber", equalTo(partner.getPhoneNumber())))
                .andExpect(jsonPath("$.fileDTO.id", equalTo(partner.getFileDTO().getId().toString())))
                .andExpect(jsonPath("$.workingPeriodDTOList.[*].id", hasItems(workingPeriodDTO.getId().toString())))
                .andExpect(jsonPath("$.address", equalTo(partner.getAddress())))
                .andExpect(jsonPath("$.locality", equalTo(partner.getLocality())));

    }

    @Test
    public void findAll() throws Exception {

    	Address address = new Address();
    	address.setId(UUID.randomUUID());
    	address.setPublicPlace("Rua Luiza Zequim");
    	address.setLocality("Maringa");
    	address.setFederatedUnit("PR");
    	
        PartnerDTO partner = new PartnerDTO();
        partner.setId(UUID.randomUUID());
        partner.setName("MPB Bar");
        partner.setPhoneNumber("992448023");
        partner.setAddress(address);
        
        when(this.service.findAll()).thenReturn(Arrays.asList(partner));

        this.mockMvc.perform(get("/api/partners"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id", hasItems(partner.getId().toString())))
                .andExpect(jsonPath("$.[*].name", hasItems(partner.getName())))
                .andExpect(jsonPath("$.[*].address.locality", hasItems(partner.getAddress().getLocality())))
                .andExpect(jsonPath("$.[*].address.publicPlace", hasItems(partner.getAddress().getPublicPlace())))
                .andExpect(jsonPath("$.[*].address.federatedUnit", hasItems(partner.getAddress().getFederatedUnit())))
                .andExpect(jsonPath("$.[*].phoneNumber", hasItems(partner.getPhoneNumber())));

        verify(this.service).findAll();
        verifyNoMoreInteractions(this.service);
    }
}
