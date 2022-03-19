package com.tema2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class Database {
    private final HashMap<String, TreeMap<String, Word>> data;

    public Database() {
        this.data = readFromAllFiles();
    }

    //Read all dictionaries from "./Dictionaries"
    HashMap<String, TreeMap<String, Word>> readFromAllFiles() {
        HashMap<String, TreeMap<String, Word>> dictionaries = new HashMap<>();
        final File folder = new File("./Dictionaries/");

        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("The folder Dictionaries is empty!");
            return null;
        }

        for (final File fileEntry : files) {
            String fileName = String.valueOf(fileEntry);

            //Check if in the folder Dictionaries is a file that is not
            if (!fileName.endsWith(".json")) {
                System.out.println("File '" + fileName + "' is not a json!");
                continue;
            }

            String language = fileName.substring(15, 17);

            //The key is the word in 'language' for each TreeMap:
            TreeMap<String, Word> dict = new TreeMap<>();

            //The key is the 'language' for each dictionary:
            dictionaries.put(language, dict);
            Gson gson = new Gson();
            JsonReader reader;
            try {
                reader = new JsonReader(new FileReader(fileEntry));
                ArrayList<Word> words = gson.fromJson(reader, new TypeToken<ArrayList<Word>>(){}.getType());
                for (Word word : words) {
                    dict.put(word.getWord(), word);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found exception: " + e);
            }
        }
        return dictionaries;
    }

    boolean addWord(Word word, String language) {
        TreeMap<String, Word> dict = data.get(language);
        if (dict == null) {
            System.out.println("Language " + language + " does not exist!");
            return false;
        }

        Word wordFromDict = dict.get(word.getWord());
        if (wordFromDict == null) {
            dict.put(word.getWord(), word);
            return true;
        }

        // If the word exists:
        return false;
    }

    boolean removeWord(String word, String language) {
        TreeMap<String, Word> dict = data.get(language);
        if (dict == null) {
            System.out.println("Language " + language + " does not exist!");
            return false;
        }

        return dict.remove(word) != null;
    }

    boolean addDefinitionForWord(String word, String language, Definition definition) {
        TreeMap<String, Word> dict = data.get(language);
        if (dict == null) {
            System.out.println("Language '" + language + "' does not exist!");
            return false;
        }

        Word w = dict.get(word);
        if (w == null) {
            System.out.println("The word '" + word + "' does not exist!");
            return false;
        }

        ArrayList<Definition> defs = w.getDefinitions();
        for (Definition def : defs) {
            if (definition.sameDict(def)) return false;
        }

        // If the definition is from another dictionary:
        defs.add(definition);
        return true;
    }

    boolean removeDefinition(String word, String language, String dictionary) {
        TreeMap<String, Word> dict = data.get(language);
        if (dict == null) {
            System.out.println("Language '" + language + "' does not exist!");
            return false;
        }

        Word w = dict.get(word);
        if (w == null) {
            System.out.println("Word '" + word + "' does not exist!");
            return false;
        }

        ArrayList<Definition> defs = w.getDefinitions();

        for (Definition def : defs) {
            if (def.getDict().equals(dictionary)) {
                defs.remove(def);
                return true;
            }
        }

        return false;
    }

    //Search for a translation for a word in English in dictionary dict
    static Word translateWordFromEN(String wordEN, TreeMap<String, Word> dict) {

        for (Word word : dict.values()) {
            if (wordEN.equals(word.getWord_en()))
                return word;
        }

        return null;
    }

    String translateWord(String word, String fromLanguage, String toLanguage) {
        TreeMap<String, Word> dict = data.get(fromLanguage);
        if (dict == null) {
            System.out.println("Language '" + fromLanguage + "' does not exist!");
            return null;
        }

        Word w = dict.get(word);
        if (w == null) {
            System.out.println("Word '" + word + "' does not exist!");
            return null;
        }

        String wordEN = w.getWord_en();

        dict = data.get(toLanguage);
        if (dict == null) {
            System.out.println("Language '" + toLanguage + "' does not exist!");
            return null;
        }

        Word translatedWord = Database.translateWordFromEN(wordEN, dict);
        if (translatedWord == null) {
            System.out.println("The translation for word '" + word +"' does not exist in the '" + toLanguage +
                    "' dictionary!");
            return null;
        }

        return translatedWord.getWord();
    }

    String translateSentence(String sentence, String fromLanguage, String toLanguage) {
        TreeMap<String, Word> dictFrom = data.get(fromLanguage);
        if (dictFrom == null) {
            System.out.println("Language '" + fromLanguage + "' does not exist!");
            return null;
        }

        TreeMap<String, Word> dictTo = data.get(toLanguage);
        if (dictTo == null) {
            System.out.println("Language '" + toLanguage + "' does not exist!");
            return null;
        }

        String[] words = sentence.split(" ");
        StringBuilder translatedSentence = new StringBuilder();
        for (String word : words) {
            Word w = dictFrom.get(word);
            if (w == null) {
                translatedSentence.append(word).append(" ");
            } else {

                String wordForTranslatedSentence;
                String wordEN = w.getWord_en();
                Word translatedWord = Database.translateWordFromEN(wordEN, dictTo);
                if (translatedWord == null) {
                    wordForTranslatedSentence = word;
                } else {
                    wordForTranslatedSentence = translatedWord.getWord();
                }

                translatedSentence.append(wordForTranslatedSentence).append(" ");
            }
        }

        translatedSentence.deleteCharAt(translatedSentence.length() - 1);

        return translatedSentence.toString();
    }

    ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage) {

        TreeMap<String, Word> dictFrom = data.get(fromLanguage);
        if (dictFrom == null) {
            System.out.println("Language '" + fromLanguage + "' does not exist!");
            return null;
        }

        int nrTranslations = 3;

        TreeMap<String, Word> dictTo = data.get(toLanguage);
        if (dictTo == null) {
            System.out.println("Language '" + toLanguage + "' does not exist!");
            return null;
        }

        StringBuilder[] translatedSentences = new StringBuilder[nrTranslations];
        for (int i = 0; i < nrTranslations; i++) {
            translatedSentences[i] = new StringBuilder();
        }

        String[] words = sentence.split(" ");
        for (String word : words) {

            Word w = dictFrom.get(word);
            //If the word does not exists in the dictFrom:
            if (w == null) {
                System.out.println("The word '" + word + "' is not in the dictionary!");
                for (StringBuilder translated : translatedSentences) {
                    translated.append(word).append(" ");
                }

            } else { //If the word exists in the dictFrom:
                String wordEN = w.getWord_en();

                Word translatedWord = Database.translateWordFromEN(wordEN, dictTo);
                //If the word does not exist in the dictTo:
                if (translatedWord == null) {
                    System.out.println("The translation for '" + word + "' is not in the dictionary!");
                    for (StringBuilder translated : translatedSentences) {
                        translated.append(word).append(" ");
                    }

                } else { //If the word exists in both dictionaries:

                    translatedSentences[0].append(translatedWord.getWord()).append(" ");
                    ArrayList<String> synonyms = translatedWord.getSynonyms();
                    if (synonyms.size() < nrTranslations) {
                        nrTranslations = synonyms.size() + 1;
                        translatedSentences = Arrays.copyOf(translatedSentences, nrTranslations);
                    }

                    int i = 1;
                    for (String synonymous : synonyms) {
                        if (i >= nrTranslations) break;
                        translatedSentences[i].append(synonymous).append(" ");
                        i++;
                    }
                }

            }
        }

        ArrayList<String> result = new ArrayList<>();
        //Delete the last " ":
        for (int i = 0; i < nrTranslations; i++) {
            translatedSentences[i].deleteCharAt(translatedSentences[i].length() - 1);
            result.add(translatedSentences[i].toString());
        }

        return result;
    }

    ArrayList<Definition> getDefinitionsForWord(String word, String language) {
        TreeMap<String, Word> dict = data.get(language);
        if (dict == null) {
            System.out.println("Language '" + language + "' does not exist!");
            return null;
        }

        Word w = dict.get(word);
        if (w == null) {
            System.out.println("The word '" + word + "' does not exist!");
            return null;
        }

        ArrayList<Definition> defs = w.getDefinitions();
        defs.sort(Comparator.comparingInt(Definition::getYear));

        return defs;
    }

    void exportDictionary(String language) {
        TreeMap<String, Word> dict = data.get(language);
        if (dict == null) {
            System.out.println("Language '" + language + "' does not exist!");
            return;
        }

        for (Word w : dict.values()) {
           w.getDefinitions().sort(Comparator.comparingInt(Definition::getYear));
        }

        String fileName = "./ExportedDictionaries/" + language + "_exportedDictionary.json";

        try (Writer writer = new FileWriter(fileName)) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(dict.values(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, TreeMap<String, Word>> getData() {
        return data;
    }
}
