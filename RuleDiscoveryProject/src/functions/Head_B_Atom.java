package functions;

import java.util.Random;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPNode;
import ec.gp.GPIndividual;
import main.FloatData;
import main.IntData;
import main.RuleDiscoveryProblem;
@SuppressWarnings("serial")
public class Head_B_Atom extends GPNode{
	public String toString() {
		if(targePredicate != -1)
		{
			return  "HEADB: " +RuleDiscoveryProblem.targetPredicates[targePredicate];
		}
		return "HEADB";}
	public int expectedChildren() {return 2;}
	int targePredicate;
	
	
	public Head_B_Atom()
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
		IntData rd = ((IntData)(input));
		int subject;
		int object;
		if(targePredicate == -1)
		{
			int randValue;
			Random randomNum = new Random();
			randValue = randomNum.nextInt(RuleDiscoveryProblem.targetPredicates.length);
			targePredicate = randValue;
		}
		

		subject = RuleDiscoveryProblem.X;
		object = RuleDiscoveryProblem.Y;
		
		rd.x = subject;
		rd.y = object;
		
		 // Regular Body (not singleton copy)
	 	children[0].eval(state, thread, input, stack, individual, problem);
	 	// don't need to do anything with return data
	 	
	 	 // Regular Body (not singleton copy)
	 	children[1].eval(state, thread, input, stack, individual, problem);
	 	// don't need to do anything with return data
	 	
	    ((RuleDiscoveryProblem)problem).ruleToEvaluate.AddAtomToFront(subject, targePredicate, object); // Add head
	    
	}

}
