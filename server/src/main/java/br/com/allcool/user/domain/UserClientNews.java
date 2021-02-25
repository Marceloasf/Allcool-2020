package br.com.allcool.user.domain;

import br.com.allcool.news.domain.News;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@Table(name = "userclientnews")
@EqualsAndHashCode(of = {"id", "user", "news"})
public class UserClientNews {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userclient_id")
    private UserClient user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;
}
