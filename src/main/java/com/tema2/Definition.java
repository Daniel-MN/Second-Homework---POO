package com.tema2;

import java.util.Arrays;

public class Definition {
    String dict;
    String dictType;
    int year;
    String[] text;

    public Definition(String dict, String dictType, int year, String[] text) {
        this.dict = dict;
        this.dictType = dictType;
        this.year = year;
        this.text = text;
    }

    public String getDict() {
        return dict;
    }

    public String getDictType() {
        return dictType;
    }

    public int getYear() {
        return year;
    }

    public String[] getText() {
        return text;
    }

    public boolean sameDict(Definition def) {
        return this.dict.equals(def.getDict()) && this.dictType.equals(def.getDictType()) && this.year == def.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Definition that = (Definition) o;
        return year == that.year && dict.equals(that.dict) && dictType.equals(that.dictType) && Arrays.equals(text, that.text);
    }

    @Override
    public String toString() {
        return "Definition{" +
                "dict='" + dict + '\'' +
                ", dictType='" + dictType + '\'' +
                ", year=" + year +
                ", text=" + Arrays.toString(text) +
                '}';
    }
}
