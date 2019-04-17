/**
 * 
 */
package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * @author UC San Diego Intermediate MOOC team
 * @author Belnarto
 *
 */
public class NearbyWords implements SpellingSuggest {
	
	private dictionary.Dictionary dict;

	public NearbyWords (dictionary.Dictionary dict) 
	{
		this.dict = dict;
	}

	/** Return the list of Strings that are one modification away
	 * from the input string.  
	 * @param s The original String
	 * @return list of Strings which are nearby the original string
	 */
	public List<String> distanceOne(String s)  {
		if (s != null) {
			s = s.replaceAll("\\W", "");
		}
		List<String> retList = new ArrayList<String>();
		substitution(s, retList);
		insertions(s, retList);
		deletions(s, retList);
		return retList;
	}

	
	/** Add to the currentList Strings that are one character mutation away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void substitution(String s, List<String> currentList) {
		// for each letter in the s and for all possible replacement characters
		if (s != null && !s.isEmpty()) {
			for(int index = 0; index < s.length(); index++){
				for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
					// use StringBuffer for an easy interface to permuting the 
					// letters in the String
					StringBuffer sb = new StringBuffer(s);
					sb.setCharAt(index, (char)charCode);
	
					// if the item isn't in the list, isn't the original string, and
					// is a real word, add to the list
					if(!currentList.contains(sb.toString()) && 
							dict.isWord(sb.toString()) &&
							!s.equals(sb.toString())) {
						currentList.add(sb.toString());
					}
				}
			}
		}
	}
	
	/** Add to the currentList Strings that are one character insertion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void insertions(String s, List<String> currentList) {
		if (s != null && !s.isEmpty()) {
			for(int index = 0; index <= s.length(); index++){
				for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
					StringBuffer sb = new StringBuffer(s);
					sb = sb.insert(index, (char)charCode);
					if(!currentList.contains(sb.toString()) && dict.isWord(sb.toString()) ) {
						currentList.add(sb.toString());
					}
				}
			}
		}
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void deletions(String s, List<String> currentList) {
		if (s != null && !s.isEmpty()) {
			for(int index = 0; index < s.length(); index++){
					StringBuffer sb = new StringBuffer(s);
					sb = sb.deleteCharAt(index);
					if(!currentList.contains(sb.toString()) && dict.isWord(sb.toString()) ) {
						currentList.add(sb.toString());
					}
			}
		}
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param word The misspelled word
	 * @param numSuggestions is the maximum number of suggestions to return 
	 * @return the list of spelling suggestions
	 */
	@Override
	public List<String> suggestions(String word, int numSuggestions) {

		// initial variables
		List<String> queue = new LinkedList<String>();     // String to explore
		HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same  
														   // string multiple times
		List<String> retList = new LinkedList<String>();   // words to return
		 
		if (word == null || word.isEmpty()) {
			return retList;
		}
		
		// insert first node
		queue.add(word);
		visited.add(word);
					
		//dictionary.Dictionary dict = new dictionary.DictionaryBST();
		//dictionary.DictionaryLoader.loadDictionary(dict, "/dictionaries/dict.txt");
		NearbyWords nearByWords = new NearbyWords(dict);
		
		int size = retList.size();
		
		while (!queue.isEmpty() && size<numSuggestions) {
			List<String> bufferList = nearByWords.distanceOne(queue.remove(0));
			for (String matchedWord : bufferList) {
				if (!visited.contains(matchedWord) && size<numSuggestions) {
					retList.add(matchedWord);
					visited.add(matchedWord);
					queue.add(matchedWord);
					size = retList.size();
				}
			}
		}
		
		return retList;
	}	

}
