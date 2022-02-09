package ru.malygin.server.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.malygin.server.model.entity.core.Site;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "_crawler_settings")
public class CrawlerSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preset", nullable = false)
    private Boolean preset;

    @Column(name = "preset_name", nullable = false, unique = true)
    private String presetName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name="parallelism")
    private Integer parallelism;

    @Column(name = "timeout")
    private Integer timeout;

    @Column(name = "delay")
    private Integer delay;

    @Column(name = "reconnect")
    private Integer reconnect;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "referrer")
    private String referrer;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "crawler",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    private List<Site> sites;
}
