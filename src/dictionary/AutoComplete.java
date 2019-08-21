/**
 * interface for prediction completions
 */
package dictionary;

import java.util.List;

/**
 * @author Christine
 * @author Belnarto
 */
public interface AutoComplete {

    public List<String> predictCompletions(String prefix, int numCompletions);

}
