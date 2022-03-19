package com.tema2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Word {
    String word;
    String word_en;
    String type;
    String[] singular;
    String[] plural;
    ArrayList<Definition> definitions;

    public Word(String word, String word_en, String type, String[] singular, String[] plural, ArrayList<Definition> definitions) {
        this.word = word;
        this.word_en = word_en;
        this.type = type;
        this.singular = singular;
        this.plural = plural;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public String getWord_en() {
        return word_en;
    }

    ArrayList<String> getSynonyms() {
        ArrayList<String> synonyms = new ArrayList<>();

        for (Definition def : this.definitions) {
            if (def.getDictType().equals("synonyms")) {
                Collections.addAll(synonyms, def.getText());
            }
        }

        return synonyms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return getWord().equals(word1.getWord()) && word_en.equals(word1.word_en) && type.equals(word1.type) &&
                Arrays.equals(singular, word1.singular) && Arrays.equals(plural, word1.plural) &&
                definitions.equals(word1.definitions);
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", word_en='" + word_en + '\'' +
                ", type='" + type + '\'' +
                ", singular=" + Arrays.toString(singular) +
                ", plural=" + Arrays.toString(plural) +
                ", definitions=" + definitions.toString() +
                '}';
    }
}
