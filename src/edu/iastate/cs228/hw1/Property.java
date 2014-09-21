package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Michael G Snook
 * 
 * This class maintains properties obtained from reading a configuration file.
 * 
 * For methods that require copying values of a String array, you may   
 * write your own private method to do the copy or use the generic method
 * 
 * public static <T> T[] Arrays.copyOf(T[] original, int newLength)
 * 
 * E.g., String[] oldArray={"1","2","3"};
 * 		 String[] newArray=null; 
 * 
 * newArray = Arrays.copyOf(oldArray, oldArray.length);
 * 
 *
 */
public class Property  {
	
	/**
	 * Define three private member variables of type String[] for positive terms, negative terms, stop terms.
	 * Define one private variable of type int to keep the method for determining the sentiment scoring.
	 * Define one private variable of type double to store the minimum distance used to judge the sentiment 
	 * of a given string.
	 * 
	 */
	
	 // TODO
	/**
	 * String array of terms defined to carry a positive sentiment.
	 */
	private String[] posTerms;
	
	/**
	 * String array of terms defined to carry a negative sentiment.
	 */
	private String[] negTerms;
	
	/**
	 * String array of terms defined as stop terms.
	 */
	private String[] stopTerms;
	
	/**
	 * Used for determining the sentiment scoring method.
	 */
	private int scoreMethod;
	
	/**
	 * Double used to store the minimum distance used to judge the sentinment string.
	 */
	private double minDistance;
	
	
	/**
	 * Default constructor
	 * 
	 * Set the default values of all member variables.
	 * 
	 * By default, the array of positive terms has "fun", "happy", "positive".
	 * By default, the array of negative terms has "sad", "bad", "angry". 
	 * By default, the array of stop terms keeps "a", "an", "the".
	 * The default value of the scoring method is 0.
	 * The default value of the minimum distance is 0.5.
	 * 
	 */
	public Property() {
		posTerms = new String[] {"fun", "happy", "positive"};
		negTerms = new String[] {"sad", "bad", "angry"};
		stopTerms = new String[] {"a", "an", "the"};
		scoreMethod = 0;
		minDistance = 0.5;
		
	}
	
	/**
	 * Copy constructor; deep copy
	 * 
	 * Copy all the values of the member variables of the input Property object to this object.
	 * 
	 * @param p Property object as input
	 */
	
	public Property(Property p) {
		//Deep Copy
		posTerms = Arrays.copyOf(p.getPositiveTerms(), p.getPositiveTerms().length);
		negTerms = Arrays.copyOf(p.getNegativeTerms(), p.getNegativeTerms().length);
		stopTerms = Arrays.copyOf(p.getStopTerms(), p.getStopTerms().length);
		scoreMethod = p.getScoringMethod();
		minDistance = p.getMinDistance();
	}
	
	
	/**
	 * 
	 * Initialize member variables with values read from the given configuration file.
	 * 
	 *    The content of the file is case sensitive. 
	 *    
	 *    Set all the properties to the default values first. Read the configuration file 
	 *    and overrides the default properties if the values in the configuration file are valid.
	 *   
	 *    See the example of the configuration file in the assignment description.
	 *    
	 *    We assume that users do not provide duplicated terms for positive terms, negative terms, 
	 *    or stop terms.
	 *    
	 *    You do not need to check whether positive terms, negative terms, or stop terms overlap.
	 *    You do not need to check duplicates within each array of terms. 
	 *    
	 *    Ignore any properties that do not match the above predefined properties.
	 *       
	 *    Use try catch to handle IOException or NumberFormatException. If any of them occurs, print on the screen 
	 *    to indicate such an error and use the default values for all the properties like those set 
	 *    in the default constructor.
	 *    
	 *    You do not need to handle other exceptions.
	 *    
	 *    Properly close the file when done.
	 *    
	 *    You may introduce your own private method to keep the code concise.
	 *    No JUnit is needed for these private methods.
	 *   
	 *  @param configFileName Name of the configuration file 
	 *  @throws NullPointerException if configFileName is null
	 *          
	 */
	public Property(String configFileName) throws NullPointerException {
		//set properties to default values first
		posTerms = new String[] {"fun", "happy", "positive"};
		negTerms = new String[] {"sad", "bad", "angry"};
		stopTerms = new String[] {"a", "an", "the"};
		scoreMethod = 0;
		minDistance = 0.5;
		
		File configFile = new File(configFileName);
		try {
			//Scanner used to read the configFile
			Scanner read = new Scanner(configFile);
			while(read.hasNextLine()){
				String line = read.nextLine();
				if(line.startsWith("positive=")){
					//erase "positive=" substring, split words
					posTerms = line.substring(9).split(",");
				}
				else if(line.startsWith("negative=")){
					//erase "negative=" substring, split words
					negTerms = line.substring(9).split(",");
				}
				else if(line.startsWith("stop=")){
					//erase "stop=" substring, split words
					stopTerms = line.substring(5).split(",");
					
				}
				else if(line.startsWith("scoringmethod=")){
					//if value is 0 or 1, set that number equal to scoreMethod
					//FIXME
					//if value is 0 or 1
					if(Character.getNumericValue(line.charAt(line.length() - 1)) == 0 || 
							Character.getNumericValue(line.charAt(line.length() - 1)) == 1){
						scoreMethod = Character.getNumericValue(line.charAt(line.length() - 1));
					}
				}
				else if(line.startsWith("mindistance=")){
					//erase "mindistance", parse double. 
					minDistance = Double.parseDouble(line.substring(12));
				}
				else{ 
					System.out.println("The config file is not valid");
					break;
				}
			}
			read.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @return A copy of the array of positive terms.
	 *         May return an empty String array if the orginal array is empty.
	 *         
	 */
	
	public String[] getPositiveTerms() {
		return posTerms;
	}
	
	/**
	 * 
	 * Set the content of the positiveTerms array to the content of the newTerms array.
	 * Both arrays must have the same length and the same content once this method returns.
	 * 
	 * Make sure that positiveTerms and newTerms do not reference the same array object 
	 * since we do not want the positiveTerm Property to change unknowingly because the 
	 * content of newTerms are changed.
	 * 
	 * @param newTerms Array of String of new words to use as positive terms
	 * @throws NullPointerException when newTerms is null or the array is empty
	 * 
	 */
	public void setPositiveTerms(String[] newTerms) throws NullPointerException {
		//if null or length of 0, throw exception.
		if(newTerms == null || newTerms.length == 0){
			throw new NullPointerException();
		}
		else{
			//reference posTerms to a new, empty string array of the same length at newTerms
			posTerms = new String[newTerms.length];
			//copy each string into posTerms from newTerms, individually
			for(int i = 0; i < posTerms.length; i++){
				posTerms[i] = newTerms[i];
			}
		}
	}
	
	/**
	 * 
	 * @return A copy of the array of negative terms.
	 *         May return an empty String array if the original array is empty.
	 */
	
	public String[] getNegativeTerms() {
		return negTerms;
	}
	
	
	/**
	 * 
	 * Set the content of the negativeTerms array to the content of the newTerms array.
	 * Both arrays must have the same length and the same content once this method returns.
	 * 
	 * Make sure that negativeTerms and newTerms do not reference the same array object 
	 * since we do not want the negative terms of this Property object to change unknowingly 
	 * because newTerms are changed.
	 * 
	 * @param newTerms Array of String of new words to use as negative terms
	 * @throws NullPointerException when newTerms is null or the array is empty
	 * 
	 */
	public void setNegativeTerms(String[] newTerms) throws NullPointerException {
		//if null or length of 0, throw exception.
		if(newTerms == null || newTerms.length == 0){
			throw new NullPointerException();
		}
		else{
			//reference negTerms to a new, empty string array of the same length at newTerms
			negTerms = new String[newTerms.length];
			//copy each string into negTerms from newTerms, individually
			for(int i = 0; i < negTerms.length; i++){
				negTerms[i] = newTerms[i];
			}
		}
	}
	
	/**
	 * 
	 * @return A copy of the array of stop terms
	 * 		   May return an empty array if the orginal array is empty.
	 */
	
	public String[] getStopTerms() {
		return stopTerms;
	}
	
	/**
	 * 
	 * Set the content of the stopTerms array to the content of the newTerms array.
	 * Both arrays must have the same length and the same content once this method returns.
	 * 
	 * Make sure that stopTerms and newTerms do not reference the same array object 
	 * since we do not want the stop terms of this Property object to change unknowingly 
	 * because newTerms are changed.
	 * 
	 * @param newTerms Array of String of new words to use as stop terms
	 * @throws NullPointerException when newTerms is null or the array is empty
	 * 
	 */
	public void setStopTerms(String[] newTerms) throws NullPointerException {
		//if null or length of 0, throw exception.
		if(newTerms == null || newTerms.length == 0){
			throw new NullPointerException();
		}
		else{
			//reference stopTerms to a new, empty string array of the same length at newTerms
			stopTerms = new String[newTerms.length];
			//copy each string into stopTerms from newTerms, individually
			for(int i = 0; i < stopTerms.length; i++){
				stopTerms[i] = newTerms[i];
			}
		}
	}
	
	/**
	 * 
	 * @return Sentiment analysis scoring method
	 */
	public int getScoringMethod() {
		return scoreMethod;
	}
	
	/**
	 * 
	 * @param method Positive integer indicating which method is used for computing the sentimental score
	 * @throws IllegalArgumentException if the method value is not zero and is not 1.
	 */
	public void setScoringMethod(int method) throws IllegalArgumentException {
		//if not 0 or 1, throw exception
		if(method != 0 && method != 1){
			throw new IllegalArgumentException();
		}
		else{
			scoreMethod = method;
		}
	}
	
	/**
	 * 
	 * @return Value of the member variable keeping the minimum distance for sentiment classification
	 */
	public double getMinDistance() {
		return minDistance;
	}
	
	/**
	 * Set the value of the member variable that keeps the minimum distance to the value of newdistance.
	 * 
	 * @param newdistance New minimum distance
	 * @throws IllegalArgumentException when the newdistance is not positive.
	 */
	public void setMinDistance(double newdistance) throws IllegalArgumentException {
		if(newdistance  < 0.0){
			throw new IllegalArgumentException();
		}
		else{
			minDistance = newdistance;
		}
		
	}
	
	/**
	 * Two objects are equals if they have same values for all their private member variables: 
	 * positiveTerms, negativeTerms, stopTerms, scoringMethod, and minDistance.
	 * 
	 * Implement the method using the guidelines given in class.
	 * 
	 * For comparing two arrays, make sure that the content of the two arrays at the same index have 
	 * the same value and the two arrays have the same length. You may write your own private method 
	 * to compare or you may use Arrays.equals() or Arrays.deepEquals() for this purpose.
	 * These two methods return different results for nested arrays. 
	 * 
	 * For comparing doubles, two doubles are equal if they differ less than a small error range, 
	 * defined as 0.000001 here.
	 * 
	 * 
	 */
	public boolean equals(Object o) {
		//if classes of objects do not match or class, return false.
		if(this.getClass() != o.getClass()){
			return false;
		}
		//if posTerms, negTerms, or stopTerms are not identical
		if(!Arrays.equals(posTerms, ((Property)o).posTerms) ||
			!Arrays.equals(negTerms, ((Property)o).negTerms) ||
			!Arrays.equals(stopTerms, ((Property)o).stopTerms)){
			return false;
		}
		//if score methods are different
		if(this.scoreMethod != ((Property)o).scoreMethod){
			return false;
		}
		//if minDistances are different
		if(Math.abs(this.minDistance - ((Property)o).minDistance) >= 0.000001){
			return false;
		}
		//if all fields compared above are identical, return true
		else{
			return true;
		}
	}
	
	/**
	 * @return String "Property"
	 */
	
	public String toString() {
		return "Property";
		
	}
}
