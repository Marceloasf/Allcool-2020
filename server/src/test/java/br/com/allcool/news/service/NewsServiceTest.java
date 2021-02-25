package br.com.allcool.news.service;

import br.com.allcool.address.domain.Address;
import br.com.allcool.enums.NewsTypeEnum;
import br.com.allcool.exception.DataNotFoundException;
import br.com.allcool.news.domain.News;
import br.com.allcool.news.repository.NewsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewsServiceTest {

    @InjectMocks
    private NewsService service;

    @Mock
    private NewsRepository repository;

    @Test
    public void findById() {

        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setPublicPlace("Rua Luiza Zequim");
        address.setLocality("Maringa");
        address.setFederatedUnit("PR");

        News news = new News();
        news.setId(UUID.randomUUID());
        news.setAddress(address);
        news.setDescription("Descricao teste news");
        news.setTitle("Titulo News");
        news.setRating(BigDecimal.valueOf(4.5));
        news.setCreationDate(LocalDate.of(2020,2,5));
        news.setEventDate(LocalDateTime.of(2020,2,5,20,30));
        news.setType(NewsTypeEnum.EVENT);

        when(this.repository.findById(news.getId())).thenReturn(Optional.of(news));

        this.service.findById(news.getId());

        verify(this.repository).findById(news.getId());
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void findByIdException() {

        UUID id = UUID.randomUUID();

        when(this.repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataNotFoundException.class, () -> this.service.findById(id));

        assertThat(exception.getMessage())
                .isEqualTo("O registro não foi encontrado.");

        verify(this.repository).findById(id);
        verifyNoMoreInteractions(this.repository);
    }
}
