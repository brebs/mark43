package grizzley;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Text {

	private Locale local = Locale.US;
	private String text;	

	@XmlElement(name = "text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public float avgWord() {
		int wordCount = 0;
		float letterCount = 0;
		
		for (String word : getWords()) {
			wordCount += 1;
			letterCount += word.length();
		}
		
		if (wordCount == 0)
			return 0;
		else	
			return letterCount / wordCount;
	}
	
	/*
	 * Combines count of upper and lower cases of words
	 */
	public String mostCommon() {
		List<String> words = getWords();
		HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();
		for (String word : words) {
			Integer frequency = wordFreq.get(word.toLowerCase());
			wordFreq.put(word.toLowerCase(), (frequency != null) ? frequency + 1 : 1);
		}
		
		Integer max = 0;
		String toReturn = null;
		for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
		    Integer value = entry.getValue();
		    
		    if (value > max) {
		    	max = value;
		    	toReturn = entry.getKey();
		    }
		    else if (value == max) {
		    	toReturn = (entry.getKey().compareToIgnoreCase(toReturn) == 1) ? toReturn : entry.getKey();
		    }
		}
		
		return toReturn;
	}
	
	public float avgSentenceLength() {
		Integer sentenceCount = getSentences().size();
		float wordCount = getWords().size();
		
		if (sentenceCount == 0)
			return 0;
		else	
			return wordCount / sentenceCount;
	}
	
	/*
	 * A word is defined to only include the characters a-z, A-Z, and 0-9.
	 * Strip away all punctuation and combine this -> D.C. becomes DC, isn't becomes isnt
	 */
	private List<String> getWords() {
		List<String> toReturn = new ArrayList<String>(Arrays.asList(text.replaceAll("[^a-zA-Z_0-9 ]", "").split("\\s+")));
		toReturn.remove("");
		return toReturn;
	}

	/*
	 *  I decided to use a library to detect sentences
	 */
	public List<String> getSentences(){
	    List<String> sentences = new ArrayList<String>();
	    BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(local);      
	    sentenceIterator.setText(text);
	    int boundary = sentenceIterator.first();
	    int lastBoundary = 0;
	    while (boundary != BreakIterator.DONE) {
	        boundary = sentenceIterator.next();         
	        if(boundary != BreakIterator.DONE){
	            sentences.add(text.substring(lastBoundary, boundary));
	        }
	        lastBoundary = boundary;            
	    }
	    return sentences;
	}

	@Override
	public String toString() {
		return "SentenceJson [text=" + text + "]";
	}

}