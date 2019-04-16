/**
 * interface to provide suggestions for incorrect word
 */
package spelling;

import java.util.List;

/**
 * @author Belnarto
 *
 */
public interface SpellingSuggest {

	public List<String> suggestions(String word, int numSuggestions);
	
}
