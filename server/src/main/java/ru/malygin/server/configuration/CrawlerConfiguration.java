package ru.malygin.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.malygin.server.model.entity.CrawlerSettings;

@Configuration
@PropertySource("classpath:crawler.properties")
public class CrawlerConfiguration {

    @Value("${crawler.preset:true}")
    private boolean preset;

    @Value("${crawler.presetName:default}")
    private String presetName;

    @Value("${crawler.description:default}")
    private String description;

    @Value("${crawler.parallelism:1}")
    private Integer parallelism;

    @Value("${crawler.timeout:5000}")
    private Integer timeout;

    @Value("${crawler.delay:100}")
    private Integer delay;

    @Value("${crawler.reconnect:0}")
    private Integer reconnect;

    @Value("${crawler.userAgent:Mozilla/5.0}")
    private String userAgent;

    @Value("${crawler.referrer:https://www.google.com}")
    private String referrer;

    public CrawlerSettings getDefault() {
        CrawlerSettings cs = new CrawlerSettings();
        cs.setPreset(preset);
        cs.setPresetName(presetName);
        cs.setDescription(description);
        cs.setParallelism(parallelism);
        cs.setTimeout(timeout);
        cs.setDelay(delay);
        cs.setReconnect(reconnect);
        cs.setUserAgent(userAgent);
        cs.setReferrer(referrer);
        return cs;
    }
}
