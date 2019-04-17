/**
 * class to load dictionary into data structure
 */
package dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DictionaryLoader {

    /** Load all words from the dictionary file into the dictionary object
     * 
     * @param d  The dictionary object to load into
     * @param filename The path to file containing the words to load.
     */    
	public static void loadDictionary(Dictionary dict, String pathToFile)
    {

        BufferedReader reader = null;
        if (pathToFile.equals("")) {
        	return;
        }
        try {
            String nextWord;
        	InputStream in = DictionaryTrieTester.class.getResourceAsStream(pathToFile); 
            reader = new BufferedReader(new InputStreamReader(in));
            while ((nextWord = reader.readLine()) != null) {
            	nextWord = nextWord.replaceAll("^\\s+", "");
            	if (!nextWord.isEmpty()) {
	            	for (String word : nextWord.split("\\s+")) {
	            		dict.addWord(word);
	            	}
            	}
            }
        } catch (IOException e) {
            System.err.println("Problem loading dictionary file: \"" + pathToFile + "\"");
            e.printStackTrace();
        }
    }
    
    /** Load the first N words from the dictionary file into the dictionary object
     * 
     * @param d  The dictionary to load
     * @param filename The file containing the words to load.
     * @param numWords  The number of words to load.  It will load the first nWords words
     */
	public static void loadDictionary(Dictionary dict, String pathToFile, int numWords)
    {

        BufferedReader reader = null;
        if (pathToFile.equals("")) {
        	return;
        }
        try {
            String nextWord;
        	InputStream in = DictionaryTrieTester.class.getResourceAsStream(pathToFile); 
            reader = new BufferedReader(new InputStreamReader(in));
            int numLoaded = 0;
            while ((nextWord = reader.readLine()) != null && numLoaded < numWords) {
            	nextWord.replaceAll("^\\s+", "");
            	if (!nextWord.isEmpty()) {
	            	for (String word : nextWord.split("\\s+")) {
	            		if (dict.addWord(word)) {
	            			numLoaded++;
	            		}
	            	}
            	}
            }
            if (numLoaded < numWords) {
            	System.out.print("loadDicitonary Warning: End of dictionary file reached.  ");
            	System.out.println(numWords + " requested, but only " + numLoaded + " words loaded.");
            }
        } catch (IOException e) {
            System.err.println("Problem loading dictionary file: \"" + pathToFile + "\"");
            e.printStackTrace();
        }    	
    	
    }
}
