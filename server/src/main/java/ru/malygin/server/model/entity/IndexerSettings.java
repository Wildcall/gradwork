package ru.malygin.server.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import ru.malygin.server.model.entity.core.Site;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "_indexer_settings")
public class IndexerSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preset", nullable = false)
    private Boolean preset;

    @Column(name = "preset_name", unique = true, nullable = false)
    private String presetName;

    @Column(name = "description", nullable = false)
    private String description;

    @ElementCollection
    @CollectionTable(name = "_indexer_selector_weight",
            joinColumns = {@JoinColumn(name = "indexer_settings_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "selectors")
    @Column(name = "weight")
    private Map<String, Double> selectorWeight;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "indexer",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    private List<Site> sites;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IndexerSettings field = (IndexerSettings) o;
        return id != null && Objects.equals(id, field.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}