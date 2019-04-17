/**
 * class to test DictionaryBST
 */
package dictionary;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 * @author Belnarto
 *
 */
public class DictionaryLoaderTester {

	private DictionaryBST Dict = new DictionaryBST();

	/** Test with file which does not exist */
	@Test
	public void TestWrongFile() {
			DictionaryLoader.loadDictionary(Dict, "");
			assertEquals("Check number of words in case of wrong file", 0, Dict.size());
	}
	
	/** Test with empty file */
	@Test
	public void TestEmptyFile() {
			DictionaryLoader.loadDictionary(Dict, "/dictionaries/DictEmpty.txt");
			assertEquals("Check number of words in case of empty file", 0, Dict.size());
	}	
	
	/** Test with multiple words in line, also with leading spaces */
	@Test
	public void TestNumWordsInLineAndLeadingSpaces() {
			DictionaryLoader.loadDictionary(Dict, "/dictionaries/DictNumWordsInLine.txt");
			assertEquals("Check number of words in case of multiple words in line, also with leading spaces", 40, Dict.size());
	}		
	
	/** Test with file with empty lines */
	@Test
	public void TestFileWithEmptyLines() {
			DictionaryLoader.loadDictionary(Dict, "/dictionaries/DictWithEmptyLines.txt");
			assertEquals("Check number of words in case of empty lines in file", 40, Dict.size());
	}	
	
	/** Test when not enough words in dictionary */
	@Test
	public void TestFileEnded() {
			PrintStream previousConsole = System.out;
			ByteArrayOutputStream newConsole = new ByteArrayOutputStream();
			System.setOut(new PrintStream(newConsole));
			DictionaryLoader.loadDictionary(Dict, "/dictionaries/Dict4k.txt", 5000);
			assertEquals("Check that DictionaryLoader tells us that end of file reached", true, newConsole.toString().indexOf("Warning: End of dictionary file reached")>=0);
			System.setOut(previousConsole);
	}	
	
	/** Test 100 words loading */
	@Test
	public void Test100WordsLoading() {
			DictionaryLoader.loadDictionary(Dict, "/dictionaries/Dict4k.txt", 100);
			assertEquals("Check that 100 words were loaded", 100, Dict.size());
	}	
	
	/** Loading full dictionary */
	@Test
	public void TestFullDictLoading() {
			DictionaryLoader.loadDictionary(Dict, "/dictionaries/Dict4k.txt");
			assertEquals("Check that 100 words were loaded", 4438, Dict.size());
	}		
}
