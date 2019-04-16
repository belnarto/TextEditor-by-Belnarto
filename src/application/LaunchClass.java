package application;

import java.util.Random;

import dictionary.*;
import spelling.*;


public class LaunchClass {
	
	
	public String Dict100kPath = "data/Dict100k.txt";
	
	public Document getDocument(String text) {
		return new Document(text);
	}
	/*	
	public textgen.MarkovTextGenerator getMTG() {
		return new textgen.MarkovTextGeneratorLoL(new Random());
	}
	
	public spelling.WordPath getWordPath() {
		return new spelling.WPTree();
	}
	*/
	
    public AutoComplete getAutoComplete() {
    	Dictionary dict = new DictionaryTrie();
        DictionaryLoader.loadDictionary(dict, Dict100kPath);
        return (AutoComplete) dict;
    }
    
    public Dictionary getDictionary() {
    	Dictionary dict = new DictionaryTrie();
    	DictionaryLoader.loadDictionary(dict, Dict100kPath);
    	return dict;
    }
    
    public SpellingSuggest getSpellingSuggest(Dictionary dic) {
    	return new NearbyWords(dic);
    
    }
    
}
