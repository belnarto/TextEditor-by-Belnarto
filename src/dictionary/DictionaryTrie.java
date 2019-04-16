package dictionary;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * @author UC San Diego Intermediate MOOC team
 * @author Belnarto
 * Dictionary interface implementation with Trie data structure
 * Working only with lowercase letters
 */
public class DictionaryTrie implements Dictionary, AutoComplete
{
  
    private TrieNode root;
    private int size = 0;

    public DictionaryTrie()
	{
		root = new TrieNode();
	}
    
    /** Add this word to the dictionary.
     * Working only with lowercase letters
     * @param word The word to add
     * @return true if the word was added to the dictionary and wasn't already there
     */
	public boolean addWord(String word)
	{
		word = word.toLowerCase();
		char[] wordArr = word.toCharArray();
		boolean result = true;
		TrieNode bufferTrie = root;
		for (char c : wordArr) {
			if (bufferTrie.getChild(c) != null) {
				bufferTrie = bufferTrie.getChild(c);
				result = false;
			}
			else {
				bufferTrie = bufferTrie.insert(c);
				result = true;
			}
		}
		if (result || (bufferTrie.getText().equals(word) && !bufferTrie.isEndsWord())) {
			bufferTrie.setEndsWord(true);
			size++;
		}
	    return result;
	}

    /** Is this a word according to this dictionary? */
	public boolean isWord(String word) 
	{
		word = word.toLowerCase();
		TrieNode bufferTrie = TrieByWord(word);
		if (bufferTrie.isEndsWord() && bufferTrie.getText().equals(word)) {
			return true;
		}
		else {
			return false;			
		}
	}

	/** helper method to return best matched Node to given word */
	private TrieNode TrieByWord(String word) 
	{
		word = word.toLowerCase();
		char[] wordArr = word.toCharArray();
		TrieNode bufferTrie = root;
		int currChar = 0;
		while (currChar<wordArr.length && bufferTrie.getChild(wordArr[currChar]) != null) {
			bufferTrie = bufferTrie.getChild(wordArr[currChar]);
			currChar++;
		}
		return bufferTrie;
	}
	
    /** Return the number of words in the dictionary */
	public int size()
	{
		return size;
	}
	
	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 prefix = prefix.toLowerCase();
    	 List<String> resultList = new LinkedList<String>();
    	 List<TrieNode> queue = new LinkedList<TrieNode>();
    	 TrieNode bufferTrie = TrieByWord(prefix);
    	 if (!bufferTrie.getText().equals(prefix)) {
    		 return resultList;
    	 }
    	 queue.add(bufferTrie);
    	 while (!queue.isEmpty() && resultList.size()<numCompletions) {
    		 bufferTrie = queue.remove(0);
    		 if (bufferTrie.isEndsWord()) {
    			 resultList.add(bufferTrie.getText());
    		 }
    		 for (char c : bufferTrie.getValidNextCharacters()) {
    			 queue.add(bufferTrie.getChild(c)); 
    		 }
    	 }
    	 return resultList;
     }
}

/** 
 * Represents a node in a Trie
 * @author UC San Diego Intermediate Programming MOOC Team
 * @author Belnarto
 *
 */
class TrieNode {
	private HashMap<Character, TrieNode> children; 
	private String text;  // Maybe omit for space
	private boolean isWord;
	
	/** Create a new TrieNode */
	public TrieNode()
	{
		children = new HashMap<Character, TrieNode>();
		text = "";
		isWord = false;
	}
	
	/** Create a new TrieNode given a text String to store in it */
	public TrieNode(String text)
	{
		this();
		this.text = text;
	}
	
	/** Return the TrieNode that is the child when you follow the 
	 * link from the given Character 
	 * @param c The next character in the key
	 * @return The TrieNode that character links to, or null if that link
	 *   is not in the trie.
	 */
	public TrieNode getChild(Character c)
	{
		return children.get(c);
	}
	
	/** Inserts this character at this node.
	 * Returns the newly created node, if c wasn't already
	 * in the trie.  If it was, it does not modify the trie
	 * and returns null.
	 * @param c The character that will link to the new node
	 * @return The newly created TrieNode, or null if the node is already 
	 *     in the trie.
	 */
	public TrieNode insert(Character c)
	{
		if (children.containsKey(c)) {
			return null;
		}
		
		TrieNode next = new TrieNode(text + c.toString());
		children.put(c, next);
		return next;
	}
	
	/** Return the text string at this node */
    public String getText()
	{
		return text;
	}
	
    /** Set whether or not this node ends a word in the trie. */
	public void setEndsWord(boolean b)
	{
		isWord = b;
	}
	
	/** Return whether or not this node ends a word in the trie. */
	public boolean isEndsWord()
	{
		return isWord;
	}
	
	/** Return the set of characters that have links from this node */
	public Set<Character> getValidNextCharacters()
	{
		return children.keySet();
	}

}
