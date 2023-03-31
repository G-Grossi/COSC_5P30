package main;

import ec.EvolutionState;
import ec.simple.SimpleFinisher;
/**
 * A class that allows for clean up after ECJ run.  
 *
 */
public class RuleDiscoveryFinisher extends SimpleFinisher{
	
	public void finishPopulation(EvolutionState state, int result)
	{
		super.finishPopulation(state, result);
		
		// Before exiting... print out any found rules (of all the generations, of all jobs)
		RuleManager.RunTimeMeasuresStop();
		RuleManager.WriteFoundRulesToFile();
		RuleManager.WriteUnderThresholdFoundRulesToFile();
	    //RuleManager.WriteHistogramToFile();  Only do once, then read in file
		RuleManager.InitAndClearFoundRules();
	}

}
