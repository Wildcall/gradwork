package ru.malygin.server.model.entity.core;

import lombok.*;
import ru.malygin.server.model.entity.IndexerStatistics;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "_lemma",
        indexes = @javax.persistence.Index(name = "lemma_index", columnList = "lemma"))
public class Lemma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lemma", nullable = false)
    private String lemma;

    @Column(name = "frequency", nullable = false)
    private int frequency;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "lemma",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<LIndex> LIndices;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Site site;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private IndexerStatistics indexerStatistics;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lemma lemma1 = (Lemma) o;

        return this.lemma.equals(lemma1.getLemma()) && this.site.getId().equals(lemma1.getSite().getId());
    }

    @Override
    public int hashCode() {
        return lemma.hashCode();
    }

    public Lemma(String lemma, int frequency, Site site, IndexerStatistics indexerStatistics) {
        this.lemma = lemma;
        this.frequency = frequency;
        this.site = site;
        this.indexerStatistics = indexerStatistics;
    }
}
