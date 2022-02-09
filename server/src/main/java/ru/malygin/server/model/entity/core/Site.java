package ru.malygin.server.model.entity.core;

import lombok.*;
import org.hibernate.Hibernate;
import ru.malygin.server.model.entity.CrawlerSettings;
import ru.malygin.server.model.entity.IndexerSettings;
import ru.malygin.server.model.entity.CrawlerStatistics;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Table(name = "_site", indexes = @javax.persistence.Index(name = "path_index", columnList = "path"))
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SiteStatus status;

    @Column(name = "status_time", nullable = false)
    private LocalDateTime statusTime;

    @Column(name = "path", nullable = false, unique = true)
    private String path;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "site",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Page> pages;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "site",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Lemma> lemmas;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "site",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Error> errors;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "site",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<CrawlerStatistics> crawlerStatistics;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private CrawlerSettings crawler;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private IndexerSettings indexer;
}
