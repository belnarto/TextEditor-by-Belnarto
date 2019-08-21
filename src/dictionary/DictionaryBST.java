package dictionary;

import java.util.TreeSet;

/**
 * @author UC San Diego Intermediate MOOC team
 * @author Belnarto
 * Dictionary interface implementation with binary search tree
 * Working only with lowercase letters
 */
public class DictionaryBST implements Dictionary {
    private TreeSet<String> dict;

    public DictionaryBST() {
        dict = new TreeSet<String>();
    }

    /**
     * Add this word to the dictionary.
     * Working only with lowercase letters
     *
     * @param word The word to add
     * @return true if the word was added to the dictionary and wasn't already there
     */
    public boolean addWord(String word) {
        word = word.toLowerCase();
        if (dict.contains(word)) {
            return false;
        } else {
            dict.add(word);
            return true;
        }
    }

    /**
     * Is this a word according to this dictionary?
     */
    public boolean isWord(String s) {
        return dict.contains(s.toLowerCase());
    }

    /**
     * Return the number of words in the dictionary
     */
    public int size() {
        return dict.size();
    }

}
