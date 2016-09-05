package com.feliperrm.babbelproject.models;

import com.feliperrm.babbelproject.utils.Singleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by felip on 31/08/2016.
 */
public class WordSet extends ArrayList<Word> implements Serializable {

    public static final Random random = new Random(System.currentTimeMillis());

    public Word removeRandomWord(){
        if(this.size()==0)
            this.addAll(Singleton.getSingleton().getWordSet()); // If the player plays through all words, restore them to the array.
        return this.remove(random.nextInt(this.size()));
    }

    /* Has an equal chance of returning the same word, or a different word from the Word Set*/
    public Word getFallingWord(Word currentWord) {
        if(random.nextInt()%2==0)
            return currentWord;
        else
            return this.get(random.nextInt(this.size()));
    }


}
