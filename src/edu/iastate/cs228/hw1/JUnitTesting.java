package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

public class JUnitTesting {
	
	@Before
	public void setUp(){
		Property props = new Property("config1.txt");
		
		
		
		
		NaiveTextAnalyzer analyzer = new NaiveTextAnalyzer();
		analyzer.init(props);
		double totalScore = 0.0;
	}
	
	
	
	@Test
	public void testProperty(){
		Property localProps = new Property("config1.txt");
		
		
		
		//test minDistance is initialized properly
		assertTrue(localProps.getMinDistance() == 4.0);
		
		//test scoring method is initialized properly
		assertTrue(localProps.getScoringMethod() == 0);
		
		//config1 positive, negative, and stop words arrays.
		String posWords1[] = {"happy","good","fun","fine","positive"};
		String negWords1[] = {"dislike","sad","bad","sorry","regret","angry"};
		String stopWords1[] = {"I","an","a","the","of","on"};
		
		//getPositiveTerms related tests
		{
			//test to see posWords array was initialized properly
			assertTrue(Arrays.equals(localProps.getPositiveTerms(), posWords1));
			
			//don't reference the same array
			assertFalse(localProps.getPositiveTerms().equals(posWords1));
			
			//test setPositiveWords method
			localProps.setPositiveTerms(new String[] {"hello", "comedy", "coms228"});
			//arrays are not identical anymore
			assertFalse(Arrays.equals(localProps.getPositiveTerms(), posWords1));
		}
		
		//getNegativeTerms related tests
		{
			//test to see negWords array was initialized properly
			assertTrue(Arrays.equals(localProps.getNegativeTerms(), negWords1));
			
			//don't reference the same array
			assertFalse(localProps.getNegativeTerms().equals(negWords1));
			
			//tests setNegativeTerms method
			localProps.setNegativeTerms(new String[] {"hello", "comedy", "coms228"});
			//arrays are not identical anymore
			assertFalse(Arrays.equals(localProps.getNegativeTerms(), negWords1));
		}
		
		//getStopTerms related tests
		{
			//test to see stopWords array was initialized properly
			assertTrue(Arrays.equals(localProps.getStopTerms(), stopWords1));
			
			//don't reference the same array
			assertFalse(localProps.getStopTerms().equals(stopWords1));
			
			//tests setStopTerms method
			localProps.setStopTerms(new String[] {"hello", "comedy", "coms228"});
			//arrays are not identical anymore
			assertFalse(Arrays.equals(localProps.getStopTerms(), stopWords1));
		}
		
		
		//test getProperty method
		assertTrue(localProps.toString().equals("Property"));

		//tests rest of set methods 
		{
			localProps.setMinDistance(3.4);
			//Test setMinDistance method
			assertTrue(localProps.getMinDistance() == 3.4);
			
			localProps.setScoringMethod(1);
			//Test setMinDistance method
			assertTrue(localProps.getScoringMethod() == 1);
		}
		
		//test default constructor and copyConstructor
		{
			Property defaultProperty = new Property();
			
			//tests positive word array
			assertTrue(Arrays.equals(defaultProperty.getPositiveTerms(), new String[] {"fun", "happy", "positive"}));
			
			//tests negative word array
			assertTrue(Arrays.equals(defaultProperty.getNegativeTerms(), new String[] {"sad", "bad", "angry"}));
			
			//tests stop word array
			assertTrue(Arrays.equals(defaultProperty.getStopTerms(), new String[] {"a", "an", "the"}));
			
			//tests default scoreMethod
			assertTrue(defaultProperty.getScoringMethod() == 0);
			
			//Test setMinDistance method
			assertTrue(defaultProperty.getMinDistance() == 0.5);
		
		
		//tests copy constuctor
		
			Property copyProperty = new Property(defaultProperty);
			
			//tests positive word array
			assertTrue(Arrays.equals(copyProperty.getPositiveTerms(), new String[] {"fun", "happy", "positive"}));
			
			//tests negative word array
			assertTrue(Arrays.equals(copyProperty.getNegativeTerms(), new String[] {"sad", "bad", "angry"}));
			
			//tests stop word array
			assertTrue(Arrays.equals(copyProperty.getStopTerms(), new String[] {"a", "an", "the"}));
			
			//tests default scoreMethod
			assertTrue(copyProperty.getScoringMethod() == 0);
			
			//Test setMinDistance method
			assertTrue(copyProperty.getMinDistance() == 0.5);
		}
		
		//tests invalid file passed to Property(String configFileName) constructor
		{
			Property fakeProps = new Property("DoesNotExistFile.txt");
			
			//tests positive word array
			assertTrue(Arrays.equals(fakeProps.getPositiveTerms(), new String[] {"fun", "happy", "positive"}));
			
			//tests negative word array
			assertTrue(Arrays.equals(fakeProps.getNegativeTerms(), new String[] {"sad", "bad", "angry"}));
			
			//tests stop word array
			assertTrue(Arrays.equals(fakeProps.getStopTerms(), new String[] {"a", "an", "the"}));
			
			//tests default scoreMethod
			assertTrue(fakeProps.getScoringMethod() == 0);
			
			//Test setMinDistance method
			assertTrue(fakeProps.getMinDistance() == 0.5);
		}
		
		//tests equals(Object o) method
		{
			Property defProps = new Property();
			Property copyProps = new Property(defProps);
			
			//references different Property object instances
			assertFalse(copyProps == defProps);
			
			//contents of objects are identical
			assertTrue(copyProps.equals(defProps));
			
			//change something in the copy
			copyProps.setMinDistance(2.0);
			
			//objects are no longer identical
			assertFalse(copyProps.equals(defProps));
		}		
	}
	
	@Test
	public void testTextAnalyzer(){
		TextAnalyzer ta = new TextAnalyzer();
		TextAnalyzer taOther  = new TextAnalyzer();
		
		//test constructor assignments and setter method
		{			
			//test constructor
			assertTrue(ta.getInputType().equals("Text"));
			
			//test constructor
			assertTrue(ta.getName().equals("Text Analyzer"));
			
			ta.setName("coms228 rocks");
			//test setter method
			assertTrue(ta.getName().equals("coms228 rocks"));
		}
		
		//test equals method
		{
			ta.setName("Text Analyzer");
			//test equals
			assertTrue(ta.equals(taOther));
			
			taOther.setName("different");
			
			//test equals
			assertFalse(ta.equals(taOther));
		}
	}
	
	
	@Test
	public void testNaiveTextAnalyzer(){
		NaiveTextAnalyzer nta1 = new NaiveTextAnalyzer();
		NaiveTextAnalyzer nta2 = new NaiveTextAnalyzer();
		
		
		//test equals method and set method
		{
			//default name is "Naive Text Sentiment Analyzer"
			assertTrue(nta1.getName().equals("Naive Text Sentiment Analyzer"));
			
			nta1.setName("hello");
			
			//test setName method
			assertTrue(nta1.getName().equals("hello"));
			
			//change back
			nta1.setName("Naive Text Sentiment Analyzer");
			
			//contain same member variable values
			assertTrue(nta1.equals(nta2));
			
			nta1.init(new Property());
			
			//should not contain the same member variable values
			assertFalse(nta1.equals(nta2));
			
			nta2.init(new Property());
			
			//contain same member variable values
			assertTrue(nta1.equals(nta2));
		}
		
		//test findSentiment
		{
			nta1.init(new Property("config1.txt"));
//			I love fun happy good fun happy good positive positive.
//			I dislike sad sad sad bad bad angry angry.
//			happy fun good sad bad regret

//			fun fun fun sorry sorry regret angry
			
			//score of line
			assertTrue(nta1.findSentiment("I love fun happy good fun happy good positive positive.") == 8.0);
			assertTrue(nta1.findSentiment("I dislike sad sad sad bad bad angry angry") == -8.0);
			assertTrue(nta1.findSentiment("happy fun good sad bad regret") == 0.0);
			assertTrue(nta1.findSentiment("") == 0.0);
			assertTrue(nta1.findSentiment("fun fun fun sorry sorry regret angry") == -1.0);	
		}
		
		//test showSentiment
		{
			assertEquals(nta1.showSentiment(55.0), "Positive");
			assertEquals(nta1.showSentiment(-55.0), "Negative");
			assertEquals(nta1.showSentiment(0.0), "Unknown");
		}		
	}
}
