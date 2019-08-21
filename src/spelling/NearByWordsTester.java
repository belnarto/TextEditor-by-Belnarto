package spelling;

/**
 * class to test NearbyWords
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dictionary.DictionaryLoader;
import dictionary.DictionaryTrie;

/**
 * @author UC San Diego MOOC team
 * @author Belnarto
 */
public class NearByWordsTester {

    // if change file or number of words also change number in testcases
    private String dict4kPath = "/dictionaries/Dict4k.txt";
    private DictionaryTrie dict = new DictionaryTrie();
    private NearbyWords nearByWords = new NearbyWords(dict);
    private List<String> retList;

    /**
     * Setting up same conditions for test case
     *
     * @throws java.lang.Exception
     */
    public void initializeTestConditions() throws Exception {
        DictionaryLoader.loadDictionary(dict, dict4kPath);
        retList = new ArrayList<String>();
    }

    /**
     * Test the substitution method
     */
    @Test
    public void TestSubstitution() {
        try {
            initializeTestConditions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        nearByWords.substitution(null, retList);
        assertEquals("Test null string", 0, retList.size());

        nearByWords.substitution("", retList);
        assertEquals("Test empty string", 0, retList.size());

        nearByWords.substitution("h", retList);
        assertEquals("Test 1 letter", 2, retList.size());

        retList = new ArrayList<String>();
        nearByWords.substitution("he", retList);
        assertEquals("Test 1+ letters", 4, retList.size());

        retList = null;
        try {
            nearByWords.substitution("he", retList);
            fail("Pointer to list is null");
        } catch (NullPointerException e) {
        }

    }

    /**
     * Test the insertions method
     */
    @Test
    public void TestInsertions() {
        try {
            initializeTestConditions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        nearByWords.insertions(null, retList);
        assertEquals("Test null string", 0, retList.size());

        nearByWords.insertions("", retList);
        assertEquals("Test empty string", 0, retList.size());

        nearByWords.insertions("h", retList);
        assertEquals("Test 1 letter", 2, retList.size());

        retList = new ArrayList<String>();
        nearByWords.insertions("he", retList);
        assertEquals("Test 1+ letters", 4, retList.size());

        retList = null;
        try {
            nearByWords.insertions("he", retList);
            fail("Pointer to list is null");
        } catch (NullPointerException e) {
        }

    }

    /**
     * Test the deletions method
     */
    @Test
    public void TestDeletions() {
        try {
            initializeTestConditions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        nearByWords.deletions(null, retList);
        assertEquals("Test null string", 0, retList.size());

        nearByWords.deletions("", retList);
        assertEquals("Test empty string", 0, retList.size());

        nearByWords.deletions("h", retList);
        assertEquals("Test 1 letter", 0, retList.size());

        retList = new ArrayList<String>();
        nearByWords.deletions("am", retList);
        assertEquals("Test 1+ letters", 1, retList.size());

        retList = null;
        try {
            nearByWords.deletions("he", retList);
            fail("Pointer to list is null");
        } catch (NullPointerException e) {
        }

    }

    /**
     * Test the distance one method
     */
    @Test
    public void TestDistanceOne() {
        try {
            initializeTestConditions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        retList = nearByWords.distanceOne(null);
        assertEquals("Test null string", 0, retList.size());

        retList = nearByWords.distanceOne("");
        assertEquals("Test empty string", 0, retList.size());

        retList = nearByWords.distanceOne("h");
        assertEquals("Test 1 letter", 4, retList.size());

        retList = nearByWords.distanceOne("am");
        assertEquals("Test 1+ letters", 6, retList.size());

        retList = nearByWords.distanceOne("hello");
        assertEquals("Test 1+ letters", 1, retList.size());

    }

    /**
     * Test the suggestions method
     */
    @Test
    public void TestSuggestions() {
        try {
            initializeTestConditions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        retList = nearByWords.suggestions(null, 10);
        assertEquals("Test null string", 0, retList.size());

        retList = nearByWords.suggestions("", 10);
        assertEquals("Test empty string", 0, retList.size());

        retList = nearByWords.suggestions("h", 10);
        assertEquals("Test 1 letter", 10, retList.size());

        retList = nearByWords.suggestions("am", 10);
        assertEquals("Test 1+ letters(1)", 10, retList.size());

        retList = nearByWords.suggestions("kang", 10);
        assertEquals("Test 1+ letters(2)", 10, retList.size());

        retList = nearByWords.suggestions("kangaro", 10);
        assertEquals("Test 1+ letters(3)", 0, retList.size());

    }

}
