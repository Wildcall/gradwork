package ru.malygin.server.model.entity.core;

import lombok.*;
import org.hibernate.Hibernate;
import ru.malygin.server.model.entity.CrawlerStatistics;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Table(name = "_error")
public class Error {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "error_time", nullable = false)
    private LocalDateTime errorTime;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Page page;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Site site;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private CrawlerStatistics crawlerStatistics;
}
