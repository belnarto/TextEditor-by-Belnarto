package application;

/** 
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 * @author Belnarto
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document {

	private String text;
	
	private int numWords;  // The number of words in the document
	private int numSentences;  // The number of sentences in the document
	private int numSyllables;  // The number of syllables in the document
	
	/** Create a new document from the given text.
	 * Because this class is abstract, this is used only from subclasses.
	 * @param text The text of the document.
	 */
	public Document(String text)
	{
		this.text = text;
		processText();
		this.numSyllables = countSyllables(text);
	}
	
	/** Returns the tokens that match the regex pattern from the document 
	 * text string.
	 * @param pattern A regular expression string specifying the 
	 *   token pattern desired
	 * @return A List of tokens from the document text that match the regex 
	 *   pattern
	 */
	private List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	/** This is a helper function that returns the number of syllables
	 * in a word.  
	 * 
	 * You will probably NOT need to add a countWords or a countSentences 
	 * method here.  The reason we put countSyllables here because we'll 
	 * use it again next week when we implement the EfficientDocument class.
	 * 
	 * For reasons of efficiency you should not create Matcher or Pattern 
	 * objects inside this method. Just use a loop to loop through the 
	 * characters in the string and write your own logic for counting 
	 * syllables.
	 * 
	 * @param word  The word to count the syllables in
	 * @return The number of syllables in the given word, according to 
	 * this rule: Each contiguous sequence of one or more vowels is a syllable, 
	 *       with the following exception: a lone "e" at the end of a word 
	 *       is not considered a syllable unless the word has no other syllables. 
	 *       You should consider y a vowel.
	 */
	private int countSyllables(String word)
	{
		return getTokens("((?!e\\b)[eioauyEIOAUY]+)|(\\b[^aioeuyEIOAUY]+e\\b)").size();
	}

	/** 
	 * Take a string that either contains only alphabetic characters,
	 * or only sentence-ending punctuation.  Return true if the string
	 * contains only alphabetic characters, and false if it contains
	 * end of sentence punctuation.  
	 * 
	 * @param tok The string to check
	 * @return true if tok is a word, false if it is punctuation. 
	 */
	private boolean isWord(String tok)
	{
		return !(tok.indexOf("!") >=0 || tok.indexOf(".") >=0 || tok.indexOf("?")>=0);
	}
	
	
    /** Passes through the text one time to count the number of words, syllables 
     * and sentences, and set the member variables appropriately.
     * Words, sentences and syllables are defined as described below. 
     */
	private void processText()
	{
		// Call getTokens on the text to preserve separate strings that are 
		// either words or sentence-ending punctuation.  Ignore everything
		// That is not a word or a sentence-ending puctuation.
		List<String> tokens = getTokens("[!?.]+|[a-zA-Z]+");
		for (String token : tokens) {
			if (isWord(token)) {
				numWords ++;
			}
		}
		numSentences = tokens.size() - numWords;
		numSyllables = getNumSyllables();
	}

	
	private int getNumSentences() {
		return numSentences;
	}
	
	private int getNumWords() {
		return numWords;
	}
	
	private int getNumSyllables() {
		return numSyllables;
	}
	
	/** Return the entire text of this document */
	public String getText()
	{
		return this.text;
	}
	
	/** return the Flesch readability score of this document */
	public double getFleschScore()
	{
		if (text.isEmpty()) {
			return 0.0;
		}
		if (numSentences == 0) {
			numSentences = 1;
		}
		if (numSyllables == 0) {
			numSyllables = 1;
		}
		double result = 0;
		result = 206.835 - 1.015*((double)this.getNumWords()/(double)this.getNumSentences())-84.6*((double)getNumSyllables()/(double)getNumWords());
		return result;
	}
	
	
	
}
