package ru.malygin.server.utility;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.*;

public class Lemmantisator {

    /**
     * Метод разбивает исходный текст на отдельные леммы, считает количество одинаковых лем и умножает количество на весовой коэффициент.
     * Возвращает пары лемма и её суммарный вес в данном тексте
     * @param text исходный текст
     * @param weight весовой коэффициент для данного текста
     * @return Map< String, Double >
     */
    public static Map<String, Double> getLemmasWeight(String text, Double weight) {
        Map<String, Double> lemmas = new HashMap<>();
        String[] textList = prepareText(text);
        if (textList == null) {
            return lemmas;
        }
        try {
            LuceneMorphology luceneMorphology = new RussianLuceneMorphology();
            Arrays.stream(textList).toList().forEach(item-> {
                if (item.isEmpty() || item.isBlank())
                    return;
                        luceneMorphology.getNormalForms(item).forEach(word -> {
                            List<String> info = luceneMorphology.getMorphInfo(word);
                            boolean skipWord = false;
                            for (String tmp: info) {
                                if (tmp.contains("СОЮЗ") || tmp.contains("МЕЖД") || tmp.contains("ПРЕДЛ")) {
                                    skipWord = true;
                                    break;
                                }
                            }
                            if (!skipWord) {
                                lemmas.compute(word, (key, val) -> (val == null) ? weight : val + weight);
                            }
                        });
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lemmas;
    }

    /**
     * Метод разбивает исходный текст на отдельные леммы, и возвращает список лем.
     * @param text исходный текст
     * @return List< String >
     */
    public static List<String> getLemmas(String text) {
        return getLemmasWeight(text, 1.0).keySet().stream().toList();
    }

    private static String[] prepareText(String text) {
        return (text == null || text.isEmpty()) ? null : text
                .replaceAll("[^А-Яа-я\\s]" ,"")
                .replaceAll("(\\s)+", " ")
                .toLowerCase(Locale.ROOT)
                .split(" ");
    }
}
