package br.com.allcool.news.repository;

import br.com.allcool.address.domain.Address;
import br.com.allcool.enums.NewsTypeEnum;
import br.com.allcool.file.domain.File;
import br.com.allcool.news.domain.News;
import br.com.allcool.test.RepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RunWith(SpringRunner.class)
@Sql(scripts = {"/sql/address/address.sql", "/sql/file/file.sql", "/sql/news/news.sql"})
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class NewsRepositoryTest {

    @Autowired
    private NewsRepository repository;

    private final UUID NEWS_ID = UUID.fromString("33383cb0-07bd-4b49-beda-55f9682b8e70");

    @Test
    public void findAll() {

        List<News> newsList = this.repository.findAll();

        assertThat(newsList).hasSize(2);
        assertThat(newsList).extracting(news -> news.getAddress().getId()).containsExactly(UUID.fromString("c610a5c3-9746-43be-a1e4-5435411b0328"), UUID.fromString("28fd5d3c-97b2-11ea-bb37-0242ac130002"));
        assertThat(newsList).extracting(news -> news.getFile().getId()).containsExactly(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"), UUID.fromString("d33686e0-963e-11ea-bb37-0242ac130002"));
        assertThat(newsList).extracting(News::getDescription).containsExactly("Noticia Teste 1", "Noticia Teste 2");
        assertThat(newsList).extracting(news -> news.getRating().toString()).containsExactly("5.0000", "1.0000");
        assertThat(newsList).extracting(News::getCreationDate).containsExactly(LocalDate.of(2020,5,14), LocalDate.of(2020,5,18));
        assertThat(newsList).extracting(News::getEventDate).containsExactly(LocalDateTime.of(2020,6,15,1,0,0), LocalDateTime.of(2020,7,12,2,0,0));
        assertThat(newsList).extracting(News::getType).containsExactly(NewsTypeEnum.PRODUCT_LAUNCH, NewsTypeEnum.PRODUCT_LAUNCH);
    }

    @Test
    public void delete() {

        List<News> newsListBeforeDelete = this.repository.findAll();

        assertThat(newsListBeforeDelete).hasSize(2);

        this.repository.deleteById(NEWS_ID);

        List<News> newsListAfterDelete = this.repository.findAll();

        assertThat(newsListAfterDelete).hasSize(1);
    }

    @Test
    public void save() {

        Address address = new Address();
        address.setId(UUID.fromString("c610a5c3-9746-43be-a1e4-5435411b0328"));

        File file = new File();
        file.setId(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"));

        News news = new News();
        news.setAddress(address);
        news.setFile(file);
        news.setDescription("Uma notícia Teste");
        news.setRating(BigDecimal.ONE);
        news.setCreationDate(LocalDate.of(2020,1,1));
        news.setEventDate(LocalDateTime.of(2020,2,5,20,30));
        news.setType(NewsTypeEnum.EVENT);

        News savedNews = this.repository.saveAndFlush(news);

        assertThat(savedNews.getId()).isNotNull();
        assertThat(savedNews.getAddress()).isEqualTo(news.getAddress());
        assertThat(savedNews.getFile()).isEqualTo(news.getFile());
        assertThat(savedNews.getDescription()).isEqualTo(news.getDescription());
        assertThat(savedNews.getRating()).isEqualTo(news.getRating());
        assertThat(savedNews.getCreationDate()).isEqualTo(news.getCreationDate());
        assertThat(savedNews.getEventDate()).isEqualTo(news.getEventDate());
        assertThat(savedNews.getType()).isEqualTo(news.getType());
    }

    @Test
    public void update() {

        Address address = new Address();
        address.setId(UUID.fromString("c610a5c3-9746-43be-a1e4-5435411b0328"));

        File file = new File();
        file.setId(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"));

        News newsBeforeUpdate = this.repository.findById(NEWS_ID).get();

        assertThat(newsBeforeUpdate.getId()).isEqualTo(NEWS_ID);
        assertThat(newsBeforeUpdate.getAddress().getId()).isEqualTo(UUID.fromString("c610a5c3-9746-43be-a1e4-5435411b0328"));
        assertThat(newsBeforeUpdate.getFile().getId()).isEqualTo(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"));
        assertThat(newsBeforeUpdate.getDescription()).isEqualTo("Noticia Teste 1");
        assertThat(newsBeforeUpdate.getRating()).hasToString("5.0000");
        assertThat(newsBeforeUpdate.getCreationDate()).isEqualTo(LocalDate.of(2020,5, 14));
        assertThat(newsBeforeUpdate.getEventDate()).isEqualTo(LocalDateTime.of(2020, 6, 15, 1,00,00));
        assertThat(newsBeforeUpdate.getType()).isEqualTo(NewsTypeEnum.PRODUCT_LAUNCH);

        newsBeforeUpdate.setDescription("Noticia Teste 1 Atualizada");
        newsBeforeUpdate.setRating(BigDecimal.ONE);
        newsBeforeUpdate.setCreationDate(LocalDate.of(2021,5,14));
        newsBeforeUpdate.setEventDate(LocalDateTime.of(2022,6,15,1,1,2));
        newsBeforeUpdate.setType(NewsTypeEnum.FEATURE);

        News newsAfterUpdate = this.repository.saveAndFlush(newsBeforeUpdate);

        assertThat(newsAfterUpdate.getId()).isEqualTo(NEWS_ID);
        assertThat(newsAfterUpdate.getAddress().getId()).isEqualTo(UUID.fromString("c610a5c3-9746-43be-a1e4-5435411b0328"));
        assertThat(newsAfterUpdate.getFile().getId()).isEqualTo(UUID.fromString("ce396aea-963e-11ea-bb37-0242ac130002"));
        assertThat(newsAfterUpdate.getDescription()).isEqualTo("Noticia Teste 1 Atualizada");
        assertThat(newsAfterUpdate.getRating()).hasToString("1");
        assertThat(newsAfterUpdate.getCreationDate()).isEqualTo(LocalDate.of(2021,5,14));
        assertThat(newsAfterUpdate.getEventDate()).isEqualTo(LocalDateTime.of(2022,6,15,1,1,2));
        assertThat(newsAfterUpdate.getType()).isEqualTo(NewsTypeEnum.FEATURE);
    }
}