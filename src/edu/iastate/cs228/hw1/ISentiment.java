 package edu.iastate.cs228.hw1;
/**
 * 
 * @author tavanapo
 * 
 * Methods for implementing sentiment analysis
 * 
 */
public interface ISentiment {
	void init(Property o);        		// initialization with a Property object
	Property getProperty();				// return a reference to the Property object
    double findSentiment(String s); 	// find the sentiment score of the string s
    String showSentiment(double score); // show text corresponding to the sentiment score
}
