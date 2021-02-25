package br.com.allcool.news.repository;


import br.com.allcool.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
}
