package ru.malygin.server.model.entity.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import ru.malygin.server.model.entity.CrawlerStatistics;
import ru.malygin.server.model.entity.IndexerStatistics;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "_page",
        indexes = @javax.persistence.Index(name = "path_index", columnList = "path"))
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path", length = 768, nullable = false)
    private String path;

    @Column(name = "code", nullable = false)
    private int code;

    @Column(name = "content", length = 16777215, nullable = false)
    private String content;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @Column(name = "has_index", nullable = false)
    private boolean hasIndex;

    @Column(name = "blacklist", nullable = false)
    private boolean blacklist;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "page",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<LIndex> LIndices;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "page",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Error> errors;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Site site;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private CrawlerStatistics crawlerStatistics;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private IndexerStatistics indexerStatistics;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Page page = (Page) o;
        return id != null && Objects.equals(path, page.path);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
