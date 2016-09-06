package com.feliperrm.babbelproject.models;

import java.io.Serializable;

/**
 * Created by felip on 31/08/2016.
 */
public class Word implements Serializable {

    private String text_eng;
    private String text_spa;

    public Word() {
    }

    public String getText_eng() {
        return text_eng;
    }

    public void setText_eng(String text_eng) {
        this.text_eng = text_eng;
    }

    public String getText_spa() {
        return text_spa;
    }

    public void setText_spa(String text_spa) {
        this.text_spa = text_spa;
    }

    public static boolean isTranslationCorrect(Word first, Word second){
        if(first.getText_eng().equals(second.getText_eng()))
            return true;
        else
            return false;
    }

}
