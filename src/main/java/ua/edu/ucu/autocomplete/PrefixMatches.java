package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        //returns ammount of words added
        int counter = 0;
        for (String string : strings) { //iterates potential array
            String[] splitedStr = string.trim().split("\\s+"); // trims and splits a potential string of words
            for (String word : splitedStr) {  //forms tuple to fit the nodes structure in Trie class
                if (getWordValue(word) < 3) continue; //condition of words length 2+ characters
                Tuple t = new Tuple(word, getWordValue(word));
                this.trie.add(t);
                ++counter;
            }
        }
        return counter;
    }


    private int getWordValue(String word) {
        return word.length();
    }

    public boolean contains(String word) {
        return this.trie.contains(word);
    }

    public boolean delete(String word) {
        return this.trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() > 1) {
            return this.trie.wordsWithPrefix(pref);
        }
        return new ArrayList<String>();
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() > 1) {
            ArrayList<String> result = new ArrayList<>();
            Iterator<String> rawIter = this.trie.wordsWithPrefix(pref).iterator();
            String word;
            while (rawIter.hasNext()) {
                word = rawIter.next();
                if (word.length() < 3 + k) {
                    result.add(word);
                } else break; // use this condition from properties of our BFS sequence not to decrease size
            }
            return result;
        }
        return new ArrayList<String>();
    }

    public int size() {
        return this.trie.size();
    }
}
