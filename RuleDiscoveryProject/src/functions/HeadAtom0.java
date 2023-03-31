package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPNode;
import ec.gp.GPIndividual;
import main.FloatData;
import main.IntData;
import main.RuleDiscoveryProblem;
import java.util.*;
@SuppressWarnings("serial")
public class HeadAtom0 extends GPNode{
	public String toString() {
			
		if(targePredicate != -1)
		{
			return  "HEAD0 : " + RuleDiscoveryProblem.targetPredicates[targePredicate];
		}
		return "HEAD0 ";}
		
	
	public int expectedChildren() {return 0;}
	
	int targePredicate;
	
	
	public HeadAtom0()
	{
		targePredicate = -1;
	}
	
	public void eval(final EvolutionState state,
			final int thread,
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem)
	
	{
		int subject;
		int predicate;
		int object;

		
		IntData rd = ((IntData)(input));
		
		if(targePredicate == -1)
		{
			int randValue;
			Random randomNum = new Random();
			randValue = randomNum.nextInt(RuleDiscoveryProblem.targetPredicates.length);
			targePredicate = randValue;
		}
		
		subject = RuleDiscoveryProblem.X;
		object = RuleDiscoveryProblem.Y;
	
		
	    ((RuleDiscoveryProblem)problem).ruleToEvaluate.AddAtomToFront(subject, targePredicate, object); 
	    
	   
	}

}
