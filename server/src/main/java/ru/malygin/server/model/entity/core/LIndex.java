package ru.malygin.server.model.entity.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.malygin.server.model.entity.IndexerStatistics;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "_lindex",
        indexes = {
            @javax.persistence.Index(name = "page_indexes", columnList = "page_id"),
            @javax.persistence.Index(name = "lemma_indexes", columnList = "lemma_id")
})
public class LIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "_rank", nullable = false)
    private double rank;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Page page;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Lemma lemma;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private IndexerStatistics indexerStatistics;

    public LIndex(double rank, Page page, Lemma lemma, IndexerStatistics indexerStatistics) {
        this.rank = rank;
        this.page = page;
        this.lemma = lemma;
        this.indexerStatistics = indexerStatistics;
    }
}
