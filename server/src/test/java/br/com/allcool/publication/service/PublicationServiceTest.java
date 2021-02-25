package br.com.allcool.publication.service;

import br.com.allcool.person.domain.Person;
import br.com.allcool.product.domain.Product;
import br.com.allcool.publication.domain.Publication;
import br.com.allcool.publication.repository.PublicationRepository;
import br.com.allcool.review.domain.Review;
import br.com.allcool.user.domain.UserClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PublicationServiceTest {

    @Mock
    private PublicationRepository repository;

    @InjectMocks
    private PublicationService service;

    @Test
    public void findAll() {

        Person person = new Person();

        UserClient userClient = new UserClient();
        userClient.setPerson(person);

        Product product = new Product();

        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setUser(userClient);
        review.setProduct(product);

        Publication publication = new Publication(review);

        when(this.repository.findAll()).thenReturn(Collections.singletonList(publication));

        this.service.findAll();

        verify(this.repository).findAll();
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    public void findAllReviewPublicationsByUserId() {

        UUID userId = UUID.randomUUID();

        when(this.repository.findAllByReviewUserId(userId)).thenReturn(new ArrayList<>());

        this.service.findAllReviewPublicationsByUserId(userId);

        verify(this.repository).findAllByReviewUserId(userId);
        verifyNoMoreInteractions(this.repository);
    }
}