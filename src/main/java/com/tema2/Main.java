package com.tema2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Main {

    static void checkAddWord(Database database) {
        ArrayList<Definition> defs = new ArrayList<>();
        defs.add(new Definition("DEX", "synonyms",
                2009, new String[]{"locomotivă", "autovehicul", "automobil"}));
        Word word1 = new Word("mașină", "car", "noun", new String[]{"mașină"}, new String[]{"mașini"},
                defs);

        if (database.addWord(word1, "ro")) {
            System.out.println("'" + word1.getWord() + "' was added!");
        } else {
            System.out.println(" '" + word1.getWord() + "' exists!");
        }

        Definition def1 = new Definition("Larousse", "synonyms", 2000,
                new String[]{"greffier", "mistigri", "matou", "minet"});
        Definition def2 = new Definition("Larousse", "definitions", 2000,
                new String[]{"Mammifère carnivore (félidé), sauvage ou domestique, au museau court et arrondi",
                        "Familier. Terme d'affection, de tendresse adressé à quelqu'un",
                        "Jeu d'enfants dans lequel un des joueurs, le chat, poursuit les autres et tente de les toucher"});
        ArrayList<Definition> defs2 = new ArrayList<>();
        defs2.add(def1);
        defs2.add(def2);

        Word word2 = new Word("chat", "cat", "noun", new String[]{"chat"}, new String[]{"chats"},
                defs2);

        if (database.addWord(word2, "fr")) {
            System.out.println("'" + word2.getWord() + "' was added!");
        } else {
            System.out.println("'" + word2.getWord() + "' exists!");
        }

        for (TreeMap<String, Word> value : database.getData().values()) {
            for (Word word : value.values()) {
                System.out.println(word);
            }
        }
        System.out.println("----\n\n\n----\n");
    }

    static void checkRemoveWord(Database database) {

        if (database.removeWord("mașină", "ro")) {
            System.out.println("'mașină' was removed!");
        } else {
            System.out.println("'mașină' does not exist!");
        }

        if (database.removeWord("unmotquin'existepas", "fr")) {
            System.out.println("'unmotquin'existepas' was removed!");
        } else {
            System.out.println("'unmotquin'existepas' does not exist!");
        }

        for (TreeMap<String, Word> value : database.getData().values()) {
            for (Word word : value.values()) {
                System.out.println(word);
            }
        }
        System.out.println("----\n\n\n----\n");
    }

    static void checkAddDefinitionForWord(Database database) {
        Definition def = new Definition("Micul dicționar academic, ediția a II-a",
                "definitions", 2010, new String[]{"(sălbatică) Animal de pradă" +
                " cu corpul lung de aproximativ 70 cm, cu blană deasă, de culoare cenușie-gălbuie," +
                " cu vârful cozii negru precedat de două inele negre Si: (reg) mâță sălbatică " +
                "(Felix silvestris)."});

        if (database.addDefinitionForWord("pisică", "ro", def)) {
            System.out.println("A new definition for 'pisică' was added!");
        } else {
            System.out.println("Can not add a new defintion for 'pisică'");
        }

        def = new Definition("Le Dictionnaire", "definitions", 2004,
                new String[]{"Qu’est-ce que vous avez préparé pour aujourd’hui?"});
        if (database.addDefinitionForWord("unmotquin'existepas", "fr", def)) {
            System.out.println("A new definition for 'unmotquin'existepas' was added!");
        } else {
            System.out.println("Can not add a new defintion for 'unmotquin'existepas'");
        }


        for (TreeMap<String, Word> value : database.getData().values()) {
            for (Word word : value.values()) {
                System.out.println(word);
            }
        }
        System.out.println("----\n\n\n----\n");
    }

    static void checkRemoveDefinition(Database database) {
        if (database.removeDefinition("pisică", "ro", "Micul dicționar academic, ediția a II-a")) {

            System.out.println("A definition for 'pisică' was removed!");
        } else {
            System.out.println("Can not remove the definition for 'pisică'!");
        }

        if (database.removeDefinition("unmotquin'existepas", "fr", "Le Dictionnaire")) {
            System.out.println("A definition for 'unmotquin'existepas' was removed!");
        } else {
            System.out.println("Can not remove the definition for 'unmotquin'existepas'!");
        }

        for (TreeMap<String, Word> value : database.getData().values()) {
            for (Word word : value.values()) {
                System.out.println(word);
            }
        }
        System.out.println("----\n\n\n----\n");
    }

    static void checkTranslateWord(Database database) {
        System.out.print("Romanian: 'pisică' ------ French: ");
        String word = database.translateWord("pisică", "ro", "fr");
        if (word == null) {
            System.out.println("Can not translate this word!");
        } else {
            System.out.println("'" + word + "'.");
        }


        System.out.print("French: 'manger' -------- Romanian: ");
        word = database.translateWord("manger", "fr", "ro");
        if (word == null) {
            System.out.println("Can not translate this word!");
        } else {
            System.out.println("'" + word + "'.");
        }

        System.out.println("----\n\n\n----\n");
    }

    static void checkTranslateSentence(Database database) {
        String sentence = "pisică merge";
        System.out.println("Romanian: " + sentence);
        String translatedSentence = database.translateSentence(sentence, "ro", "fr");
        System.out.print("French: ");

        if (translatedSentence == null) {
            System.out.println("Can not translate '" + sentence + "'");
            return;
        } else {
            System.out.println(translatedSentence);
        }

        //'caine' does not exist in Romanian dictionary
        //'manca' does not exist in Romanian dictionary
        sentence = "chien manger";
        System.out.println("French: " + sentence);
        translatedSentence = database.translateSentence(sentence, "fr", "ro");
        System.out.print("Romanian: ");

        if (translatedSentence == null) {
            System.out.println("Can not translate '" + sentence + "'");
            return;
        } else {
            System.out.println(translatedSentence);
        }

        System.out.println("----\n\n\n----\n");
    }

    static void checkTranslateSentences(Database database) {
        ArrayList<String> translations = database.translateSentences("chat manger", "fr", "ro");
        System.out.println("Translations for 'chat manger':");
        for (String translation : translations) {
            System.out.println(translation);
        }

        System.out.println();

        translations = database.translateSentences("chat marcher", "fr", "ro");
        System.out.println("Translations for 'chat marcher':");
        for (String translation : translations) {
            System.out.println(translation);
        }

        System.out.println("----\n\n\n----\n");
    }

    static void checkGetDefinitionsForWord(Database database) {
        //Added a definition to prove that this is working
        Definition definition = new Definition("Un dictionar foarte bun",
                "definitions", 1900, new String[]{"nimic aici"});
        database.addDefinitionForWord("pisică", "ro", definition);
        System.out.println("Definitions for word 'pisică':");
        ArrayList<Definition> defs = database.getDefinitionsForWord("pisică", "ro");
        if (defs == null) {
            System.out.println("Can not find definitions for this word!");
            return;
        }
        for (Definition def : defs) {
            System.out.println(def);
        }
        database.removeDefinition("pisică", "ro", "Un dictionar foarte bun");

        System.out.println("Definitions for word 'manger':");
        defs = database.getDefinitionsForWord("manger", "fr");
        if (defs == null) {
            System.out.println("Can not find definitions for this word!");
            return;
        }
        for (Definition def : defs) {
            System.out.println(def);
        }

        System.out.println("----\n\n\n----\n");
    }

    static void checkExportDictionary(Database database) {
        for (String language : database.getData().keySet()) {
            database.exportDictionary(language);
        }
        System.out.println("There is a folder named ExportedDictionaries.");
    }

    public static void main(String[] args) {

        Database database = new Database();
        HashMap<String, TreeMap<String, Word>> data = database.getData();
        for (TreeMap<String, Word> value : data.values()) {
            for (Word word : value.values()) {
                System.out.println(word);
            }
        }
        System.out.println("----\n\n\n----\n");

        System.out.println("Add word:\n");
        Main.checkAddWord(database);
        System.out.println("Remove word:\n");
        Main.checkRemoveWord(database);
        System.out.println("Add definition for word:\n");
        Main.checkAddDefinitionForWord(database);
        System.out.println("Remove definition for word:\n");
        Main.checkRemoveDefinition(database);
        System.out.println("Translate word:\n");
        Main.checkTranslateWord(database);
        System.out.println("Translate sentence:\n");
        Main.checkTranslateSentence(database);
        System.out.println("Translate sentences:\n");
        Main.checkTranslateSentences(database);
        System.out.println("Definitions for word:\n");
        Main.checkGetDefinitionsForWord(database);
        System.out.println("Export dictionary:\n");
        Main.checkExportDictionary(database);
    }
}
