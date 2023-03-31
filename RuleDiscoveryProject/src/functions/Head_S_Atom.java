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
public class Head_S_Atom extends GPNode{
	public String toString() {
	if(targePredicate != -1)
	{
		return  "HEADS: " + RuleDiscoveryProblem.targetPredicates[targePredicate];
	}
	return "HEADS";}
	
	
	public int expectedChildren() {return 1;}
	int targePredicate;
	
	
	public Head_S_Atom()
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
		
		// Get subject and body from head and pass to singleton copy body
		rd.x = subject;
		rd.y = object;
		
		// Singleton Copy Body Atom
	 	children[0].eval(state, thread, input, stack, individual, problem);
	 	// don't need to do anything with return data
		
	 	
	    ((RuleDiscoveryProblem)problem).ruleToEvaluate.AddAtomToFront(subject, targePredicate, object); // Add head last
	    
	}

}
