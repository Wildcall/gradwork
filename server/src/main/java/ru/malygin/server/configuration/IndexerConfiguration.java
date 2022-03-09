package ru.malygin.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.malygin.server.model.entity.IndexerSettings;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
@PropertySource("classpath:indexer.properties")
public class IndexerConfiguration {

    @Value("${indexing.preset:true}")
    private boolean preset;

    @Value("${indexing.presetName:default}")
    private String presetName;

    @Value("${indexing.description:default}")
    private String description;

    @Value("${indexing.selectors:title,body}")
    private String selectors;

    @Value("${indexing.weights:1,0.8}")
    private String weights;

    /**
     * Возвращает объект со стандартными значениями настроек для алгоритма индексации.
     * @return IndexerSettings
     */
    public IndexerSettings getDefault() {
        IndexerSettings ifw = new IndexerSettings();
        ifw.setPreset(preset);
        ifw.setPresetName(presetName);
        ifw.setDescription(description);
        ifw.setSelectorWeight(getSelectorsWeight());

        return ifw;
    }

    private Map<String, Double> getSelectorsWeight () {
        Map<String, Double> result = new HashMap<>();
        String[] selectors = this.selectors.split(",");
        String[] weights = this.weights.split(",");
        try {
            if (selectors.length == weights.length) {
                for (int i = 0; i < selectors.length; i++) {
                    result.put(selectors[i].toLowerCase(Locale.ROOT).trim(), Double.parseDouble(weights[i].trim()));
                }
                return result;
            } else {
                throw new NumberFormatException("e");
            }
        } catch (NumberFormatException e) {
            return new HashMap<>() {{
                put("title", 1.0);
                put("body", 0.8);
            }};
        }
    }
}
