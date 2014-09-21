package edu.iastate.cs228.hw1;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * 
 * @author <Replace with your own name>
 * 
 * This class is a sub-class of TextAnalyzer and implements ISentiment.
 * 
 *
 */
public class NaiveTextAnalyzer extends TextAnalyzer implements ISentiment {
	
	/** 
	 * Define a private member variable of type Property to keep the reference to a Property object 
	 * storing various properties from the configuration file. 
	 */
	private Property props;
		
	/**
	 * Default constructor
	 * 
	 * Call the setName method of TextAnalyzer using "Naive Text Sentiment Analyzer" as the argument.
	 */
	public NaiveTextAnalyzer() {
		setName("Naive Text Sentiment Analyzer");
	}
	
	/**
	 * Call the setName method of Text Analyzer using the input string of this method as the argument.
	 * 
	 * @param newname
	 */
	public NaiveTextAnalyzer(String newname) {
		this.setName(newname);
	}
	
	/**
	 * Find sentiment of a given input string s. 
	 * 
	 * Follow this guideline.
	 * 
	 * - Eliminate all full stops, commas, semi-colons from s. 
	 *   You do not need to check other punctuations.
	 *   
	 * - Separate the string into words using whitespace characters [ \t\n\x0B\f\r].
	 * 
	 * - Count the number of negative words in s.
	 * - Count the number of positive words in s.
	 * - Count the number of stop words in s.
	 * - Count the total number of words in s.
	 * 
	 * - Compute the sentiment score using these counts.
	 * - See how to do so and the examples in the assignment description.
	 * 
	 * - Use getProperty() to get the reference to the Property object.
	 *  
	 *   Obtain the array of positive words, negative words, and stop words 
	 *   using the methods getPositiveTerms(), getNegativeTerms(), getStopWords() of 
	 *   the Property object.
	 * 
	 *   The scoring method is obtained by calling the method getScoringMethod() 
	 *   of the same Property object.
	 * 
	 * @param s Input string
	 * @return 0 if s is empty or 
	 *           the stop word count is equal to the number of words in the string 
	 *         Otherwise, return the sentiment score computed based on the choice of the scoring method.
	 * 
	 * @throws NullPointerException if s is null
	 *         IllegalStateException if the member variable keeping the reference to the Property object is null. 
	 *         	  
	 */

	@Override
	public double findSentiment(String s) throws NullPointerException, IllegalStateException {
		if(s == null){
			throw new NullPointerException();
		}
		if(this.getProperty() == null){
			throw new IllegalStateException();
		}
		double score = 0.0;
		double pos = 0.0;
		double neg = 0.0;
		double stops = 0.0;
		String[] posWords = props.getPositiveTerms();
		String[] negWords = props.getNegativeTerms();
		String[] stopWords = props.getStopTerms();
		
		String[] words = s.replaceAll("[^a-zA-Z ]", "").split(" ");
		
		//for each word in the line
		for(String word : words){
			//compare each word in the line to word lists
			//if word from line is pos word
			for(String posWord : posWords){
				if(word.equals(posWord)){
					pos++;
				}
			}
			
			//if word from line is a neg word
			for(String negWord : negWords){
				if(word.equals(negWord)){
					neg++;
				}
			}
			//if word from line is stop word
			for(String stopWord : stopWords){
				if(word.equals(stopWord)){
					stops++;
				}
			}
		}
		if(props.getScoringMethod() == 1){
			//score = positive words - negative divided by total wc - stop words
			score = (pos - neg) / (words.length - stops);
		}
		//scoring method = 0
		else{
			score = pos - neg;
		}
		return score;
	}
	
	/**
	 * Count the number of words in tokenArr that match the words in wordArr.
	 * 
	 * 
	 * @param wordArr Array of positive words, negative words, or stop words depending on what you want to count
	 * @param tokenArr Array of words 
	 * @return 0 if wordArr is empty or tokenArr is empty; 
	 * 			otherwise, return the count of words in tokenArr matching those in wordArr.
	 * @throws NullPointerException if wordArr or tokenArr is null
	 */
	public static int findCount(String[] wordArr, String[] tokenArr) {
		if(wordArr == null || tokenArr == null){
			throw new NullPointerException();
		}
		
		int count = 0;
		for(String s : tokenArr){
			//if word array contains the word in token array
			if(Arrays.asList(wordArr).contains(s)){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Override equals as discussed in class.
	 * Consider both name and property when comparing the objects.
	 * Your code must not break if the reference to the Property object is null. 
	 * 
	 */
	@Override
	public boolean equals(Object o) {
		//if references are the same
		if(this == (NaiveTextAnalyzer)o){
			return true;
		}
		if(this.getClass() != ((NaiveTextAnalyzer)o).getClass()){
			return false;
		}
		//compare input types between Naive Text Analyzer classes
		else if(!this.getInputType().equals(((NaiveTextAnalyzer)o).getInputType())){
			return false;
		}
		//compare input types between Naive Text Analyzer classes
		else if(!this.getName().equals(((NaiveTextAnalyzer)o).getName())){
			return false;
		}
		//if above conditions are satisfied and both properties are null, return true
		else if(this.props == null && ((NaiveTextAnalyzer)o).props == null){
			return true;
		}
		//if XOR are null, return false
		else if(this.props == null && ((NaiveTextAnalyzer)o).props != null ||
				this.props != null && ((NaiveTextAnalyzer)o).props == null){
			return false;
		}
		//compare minDistances
		else if(props.getMinDistance() != ((NaiveTextAnalyzer)o).getProperty().getMinDistance()){
			return false;
		}
		else if(props.getScoringMethod() != ((NaiveTextAnalyzer)o).getProperty().getScoringMethod()){
			return false;
		}
		//compare negative, positive, and stop term arrays, if any of them are not equal, return false.
		else if((!Arrays.equals(props.getNegativeTerms(), ((NaiveTextAnalyzer)o).getProperty().getNegativeTerms()) ||
				!Arrays.equals(props.getPositiveTerms(), ((NaiveTextAnalyzer)o).getProperty().getPositiveTerms()) ||
				!Arrays.equals(props.getStopTerms(), ((NaiveTextAnalyzer)o).getProperty().getStopTerms())) &&
				(this.props != null && ((NaiveTextAnalyzer)o).props != null)){
			return false;
		}
		//else
		return true;
	}

	/**
	 * Set the member variable of type Property to keep the given reference to the Property object.
	 * 
	 * @throws NullPointerException if o is null;
	 * 
	 */
	@Override
	public void init(Property o) throws NullPointerException {
		if(o == null){
			throw new NullPointerException();
		}
		props = o;
	}
	
	/**
	 * Show text sentiment for the given sentiment score.
	 * 
	 * @param score sentiment score
	 * @return 
	 *    "Positive" if score >= the predefined distance threshold obtained by calling getMinDistance() of the Property object
	 *    "Negative" if score <= -1 * the predefined distance threshold
	 *    "Unknown" otherwise
	 *  
	 *  @throws IllegalStateException if the member variable that keeps the reference to the Property object is null. 
	 */
	@Override
	public String showSentiment(double score) {
		if(props == null){
			throw new IllegalStateException();
		}
		else if(score >= props.getMinDistance()){
			return "Positive";
		}
		else if(score <= -1*props.getMinDistance()) {
			return "Negative";
		}
		else { 
			return "Unknown";
		}
	}
	
	/**
	 * Use the copy constructor of the Property object here.
	 * 
	 * @return  Reference to a copy of the current property object. 
	 */
	@Override
	public Property getProperty() {
		Property propCopy = new Property(props);
		return propCopy;
	}
	
}

