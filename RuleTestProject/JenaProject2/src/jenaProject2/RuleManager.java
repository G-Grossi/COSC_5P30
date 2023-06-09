package jenaProject2;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Duration;
import java.time.Instant;
/**
 * A class that manages rules (builds rule cache, prunes rules, prints rules to file).  
 *
 */

public class RuleManager {

	public static int job = 0;
	
	public static Map <String, GPRule> foundRules = new HashMap<String,GPRule>(); 
	
	public static Map <String, Integer> predicateHistogram = new HashMap<String,Integer>();
	
	public static Instant start;
	public static Instant stop;

	public static void AddRuleToFoundRules(GPRule rule)
	{
		String key = rule.GetHeadStr() + rule.GetBodyStr();
		
		if(!foundRules.containsKey(key))
		{
			foundRules.put(key, rule );
		}
		
	}
	
	
	/**
	 * 
	 * @param rule to find in cache
	 * @return processed rule already found and stored in cache
	 */
	public static GPRule HasAlreadyBeenFound(GPRule rule)
	{
		GPRule foundRule = null;
		
		String key = rule.GetHeadStr() + rule.GetBodyStr();
		
		if(foundRules.containsKey(key))
		{
			foundRule = foundRules.get(key);
		}
		
		return foundRule;
	}
	/**
	 * Looks up histogram results for predicate passed in
	 * @param predicate
	 * @return count for predicate in data base
	 */
	public static int GetPredicateHistSupport(String predicate)
	{
		int support = -1;
	
		if(predicateHistogram.containsKey(predicate))
		{
			support = predicateHistogram.get(predicate);
		}
		
		return support;
	}
	
	
	/**
	 *  Writes Discovered Rules to file
	 */
	public static void WriteFoundRulesToFile()
	{
		String pathToRawOutputFiles = "C:\\Users\\Gina Grossi\\eclipse-workspace\\JenaProject\\RuleTestProject\\JenaProject2\\TestedRules\\";
		PrintWriter outRaw;
		// Set up raw output file for writing evaluations
		try {
			outRaw = new PrintWriter(pathToRawOutputFiles+"RulesTested"+job+".txt");
			
			for (Map.Entry<String, GPRule> entry : foundRules.entrySet()) {
	            String key = entry.getKey();
	            GPRule valueRule = entry.getValue();
	            valueRule.ReportPCAToWriter(outRaw);
	        }
			
			if(start != null && stop !=null)
			{
				outRaw.println("***********************************************************");
				outRaw.println("Elapsed Time: "+ Duration.between(start, stop).toString());
				outRaw.println("***********************************************************");
			}
			
			if (outRaw != null) {
		    	outRaw.close();
		    	outRaw = null;
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 *  Writes Histogram of Predicates to file
	 */
	public static void WriteHistogramToFile()
	{
		String pathToRawOutputFiles = "C:\\Users\\Gina Grossi\\eclipse-workspace\\JenaProject\\RuleTestProject\\JenaProject2\\TestedRules\\";
		PrintWriter outRaw;
		// Set up raw output file for writing evaluations
		try {
			outRaw = new PrintWriter(pathToRawOutputFiles+"histogram.txt");
			
			for (Map.Entry<String, Integer> entry : predicateHistogram.entrySet()) {
	            String key = entry.getKey();
	            int value = entry.getValue();
	            outRaw.println(key +" " + value);
	        }
	
			if (outRaw != null) {
		    	outRaw.close();
		    	outRaw = null;
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    /** 
     * Reads (pre-processed) histogram from file
     * @throws NumberFormatException
     * @throws IOException
     */
	public static void ReadHistogramFromFile() throws NumberFormatException, IOException
	{
		
		try {
			FileReader file = new FileReader("C:\\Users\\Gina Grossi\\eclipse-workspace\\JenaProject\\RuleTestProject\\JenaProject2\\TestedRules\\histogram.txt");
		    BufferedReader reader = new BufferedReader(file);
			
			String line = "";
			String[] tokens;
			int support;
			String predicateName;
		
			while((line = reader.readLine()) != null)
			{
			    tokens = line.split(" "); // split line using spaces
			    predicateName = tokens[0];
			    support = Integer.parseInt(tokens[1]);
			    
				if(!predicateHistogram.containsKey(predicateName))
				{
					predicateHistogram.put(predicateName, support );
				}
				
			    
			    //System.out.println("Predicate: " + predicateName + " Support: " + support);
			}
		
			reader.close();
		    file.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Clear rule cache each time a new job is started
	 */
	public static void InitAndClearFoundRules()
	{
		foundRules.clear();
		job++;
	}
	
	/** 
	 * Timer start for each run
	 */
	public static void RunTimeMeasuresStart()
	{
		start = Instant.now();
	}
	/** 
	 * Timer stop for each run
	 */
	public static void RunTimeMeasuresStop()
	{
		stop = Instant.now();
	}
	
}
