package com.feliperrm.babbelproject.utils;

import com.feliperrm.babbelproject.models.WordSet;
import com.google.gson.Gson;

/**
 * Created by felip on 31/08/2016.
 */
public class Singleton {

    private static Singleton singleton;

    private WordSet wordSet;
    private static final String WORD_SET_PREF_KEY = "wordsetprefkey";


    /* Little treatment in case wordSet gets null during execution time, as GC might free this from the Singleton, though very unlikely*/
    public WordSet getWordSet() {
        if(wordSet==null || wordSet.isEmpty())
            wordSet = new Gson().fromJson(Util.loadSharedPreference(MyApp.getContext(), WORD_SET_PREF_KEY, null), WordSet.class);
        return wordSet;
    }

    public void setWordSet(WordSet wordSet) {
        this.wordSet = wordSet;
        Util.salvarSharedPreference(MyApp.getContext(), WORD_SET_PREF_KEY, new Gson().toJson(wordSet));
    }

    public static Singleton getSingleton(){
        if(singleton == null)
            singleton = new Singleton();
        return singleton;
    }
}
