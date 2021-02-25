package br.com.allcool.news.service;

import br.com.allcool.converter.NewsDTOConverter;
import br.com.allcool.dto.NewsDTO;
import br.com.allcool.exception.DataNotFoundException;
import br.com.allcool.news.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository repository;

    public NewsService(NewsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public NewsDTO findById(UUID id) {

        return new NewsDTOConverter().to(this.repository.findById(id).orElseThrow(DataNotFoundException::new));
    }
}
