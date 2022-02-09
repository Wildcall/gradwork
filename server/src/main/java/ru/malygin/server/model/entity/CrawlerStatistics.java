package ru.malygin.server.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.malygin.server.model.entity.core.Error;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.model.entity.core.Site;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "_crawler_statistics")
public class CrawlerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "starting_time", nullable = false)
    private LocalDateTime startingTime;

    @Column(name = "ending_time", nullable = false)
    private LocalDateTime endingTime;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Site site;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "crawlerStatistics",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Page> pages = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(
            mappedBy = "crawlerStatistics",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Error> errors = new ArrayList<>();
}
