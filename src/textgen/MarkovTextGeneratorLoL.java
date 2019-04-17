package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 * @author Belnarto
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
		
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		String[] sourceWords = sourceText.split("\\s+");

		wordList.add(new ListNode(sourceWords[0],sourceWords[1]));
		
		for (int i=1; i<sourceWords.length-1; i++ ) {
			
			int listNodeNum = 0;
			
			while ( !wordList.get(listNodeNum).getWord().equals(sourceWords[i]) && listNodeNum < wordList.size()-1)
				listNodeNum++;
			
			if (!wordList.get(listNodeNum).getWord().equals(sourceWords[i]) && listNodeNum == wordList.size()-1) {
				wordList.add(new ListNode(sourceWords[i],sourceWords[i+1]));
			} 
			else {
				wordList.get(listNodeNum).addNextWord(sourceWords[i+1]);
			}
			
		}
		
		int listNodeNum = 0;
		
		while ( !wordList.get(listNodeNum).getWord().equals(sourceWords[sourceWords.length-1]) && listNodeNum < wordList.size()-1)
			listNodeNum++;
		
		if (!wordList.get(listNodeNum).getWord().equals(sourceWords[sourceWords.length-1])) {
			wordList.add(new ListNode(sourceWords[sourceWords.length-1],sourceWords[0]));
		} 
		else {
			wordList.get(listNodeNum).addNextWord(sourceWords[0]);
		}
		
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		String ouput = "";
		ListNode bufferNode = wordList.get(0);
		String word = bufferNode.getWord();
		ouput += word + " ";
		for  (int i=1; i<numWords; i++) {
			word = bufferNode.getRandomNextWord(new Random());
			ouput += word + " ";
			bufferNode = getListNodeByWord(wordList, word);
		}
		return ouput;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		wordList = new LinkedList<ListNode>();
		train(sourceText);
	}
	
	public static ListNode getListNodeByWord(List<ListNode> wordList, String word)
	{
		int index = 0;
		ListNode bufferNode = wordList.get(index);
		while (!bufferNode.getWord().equals(word))
		{
			bufferNode = wordList.get(index);
			index++;
		}
		return bufferNode;
	}
	
}

/** Links a word to the next words in the list  */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	ListNode(String word, String nextWord)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
		nextWords.add(nextWord);
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		String randomWord = nextWords.get(generator.nextInt(nextWords.size()));
	    return randomWord;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


