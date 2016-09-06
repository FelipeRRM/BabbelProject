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

    private int highScore;
    private static final String HIGH_SCORE_PREF_KEY = "highscoreprefkey";

    private boolean isTutorialSeen;
    private static final String IS_TUTORIAL_SEEN_KEY = "istutorialseenprefkey";

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

    public int getHighScore() {
        if(highScore==0)
            highScore = Integer.parseInt(Util.loadSharedPreference(MyApp.getContext(), HIGH_SCORE_PREF_KEY, String.valueOf(0)));
        return highScore;
    }

    public boolean isTutorialSeen() {
        if(!isTutorialSeen)
            isTutorialSeen = Boolean.parseBoolean(Util.loadSharedPreference(MyApp.getContext(), IS_TUTORIAL_SEEN_KEY, String.valueOf(false)));
        return isTutorialSeen;
    }

    public void setTutorialSeen(boolean tutorialSeen) {
        Util.salvarSharedPreference(MyApp.getContext(), IS_TUTORIAL_SEEN_KEY, String.valueOf(tutorialSeen));
        isTutorialSeen = tutorialSeen;
    }

    public void setHighScore(int highScore) {
        Util.salvarSharedPreference(MyApp.getContext(), HIGH_SCORE_PREF_KEY, String.valueOf(highScore));
        this.highScore = highScore;
    }

    public Singleton() {
    }

    public static Singleton getSingleton(){
        if(singleton == null)
            singleton = new Singleton();
        return singleton;
    }
}
